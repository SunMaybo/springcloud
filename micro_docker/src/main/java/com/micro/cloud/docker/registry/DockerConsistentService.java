package com.micro.cloud.docker.registry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

/**
 * Created by maybo on 2016/12/26.
 */
public class DockerConsistentService {

    private Logger logger=Logger.getLogger(DockerConsistentService.class);

    @Autowired
    private DockerService dockerService;

    @Autowired
    private RegisterService registerService;

    @Scheduled(cron="0/12 * * * * ?")
    public void consistent(){
        logger.info("开启定时任务的执行：");
        Map<String,Object>desServiceMap=registerService.services();
        Map<String,Object>srcServiceMap =servicesFromDocker();
           Iterator srcIterator= srcServiceMap.keySet().iterator();
            while (srcIterator.hasNext()){
               String key=(String)srcIterator.next();
                if (!desServiceMap.containsKey(key)){
                    logger.info("注册服务:"+key);
                    registerService.register((Service) srcServiceMap.get(key));
                }
            }

            Iterator desIterator= desServiceMap.keySet().iterator();
            while (desIterator.hasNext()){
                String key=(String)desIterator.next();
                if (!srcServiceMap.containsKey(key)){
                    logger.info("取消服务:"+key);
                    registerService.deRegister(key);
                }
            }

    }
    public Map<String,Object> servicesFromDocker(){
       Map<String,Object>portMap=new  HashMap<String,Object>();
        Map<String,Object> serviceMap=new HashMap<String,Object>();
        try {
            List<Map> nodes = dockerService.nodes();
            List<Map> serviceMaps= dockerService.services();
            if (null == serviceMaps) {
                return null;
            }else{
                for (Map map:serviceMaps){
                    Map<String,Object>specMap=(Map)map.get("Spec");
                    String serviceName=null;
                    int port=0;
                    if (null!=specMap) {
                        serviceName = (String) specMap.get("Name");
                    }
                    Map<String,Object>endPointMap=(Map<String,Object>)map.get("Endpoint");
                    List<Map> mapList=(List<Map>)endPointMap.get("Ports");
                    if (null!=mapList&&mapList.size()!=0){
                        port= (int)mapList.get(0).get("PublishedPort");
                    }
                    if (null!=serviceName&&serviceName.length()!=0){
                        portMap.put(serviceName,port==0?80:port);
                    }
                }
            }
            if (null == nodes) {
                return null;
            } else {
                for (Map map : nodes) {
                    Map<String, Object> managerStatusMap = (Map<String, Object>) map.get("ManagerStatus");
                    if (null != managerStatusMap) {
                        String addr = (String) managerStatusMap.get("Addr");
                        if (null != addr) {
                            String host = addr.split(":")[0];
                            List<Map> mapList = dockerService.containers(host, 2375);
                            if (null != mapList) {
                                    for (Map<String,Object> objectMap:mapList){
                                        Service service=new Service();
                                      Map<String,Object> labelMap=(Map<String,Object>)objectMap.get("Labels");
                                        if (null!=labelMap){
                                           String serviceName=(String)labelMap.get("com.docker.swarm.service.name");
                                            if (serviceName.contains("gateway")||serviceName.contains("consul")|| serviceName.contains("nginx")){
                                                continue;
                                            }
                                            service.setServiceName(serviceName);
                                            service.setPort((int)portMap.get(serviceName));
                                        }
                                        Map<String,Object> networkSettingMap=(Map<String,Object>)objectMap.get("NetworkSettings");
                                        if (null!=networkSettingMap){
                                           Map networkMaps=(Map<String,Object>)networkSettingMap.get("Networks");
                                            Map<String,Object> ingress=(Map)networkMaps.get("ingress");
                                           String ipAddress=(String)ingress.get("IPAddress");
                                            service.setHost(ipAddress);
                                            service.setServiceId(getServiceId(service.getServiceName(),service.getHost(),service.getPort()));
                                            serviceMap.put(service.getServiceId(),service);
                                        }
                                    }
                            }
                        }
                    }
                }
            }
            return serviceMap;
        }catch (Exception e){
            return null;
        }
}

    private String getServiceId(String serviceName,String host,int port) {

        String instanceId = normalizeForDns(serviceName+":"+ host+":"+port);

        return instanceId;
    }
    private static String normalizeForDns(String s) {
        if(s != null && Character.isLetter(s.charAt(0)) && Character.isLetterOrDigit(s.charAt(s.length() - 1))) {
            StringBuilder normalized = new StringBuilder();
            Character prev = null;
            char[] var3 = s.toCharArray();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                char curr = var3[var5];
                Character toAppend = null;
                if(Character.isLetterOrDigit(curr)) {
                    toAppend = Character.valueOf(curr);
                } else if(prev == null || prev.charValue() != 45) {
                    toAppend = Character.valueOf('-');
                }

                if(toAppend != null) {
                    normalized.append(toAppend);
                    prev = toAppend;
                }
            }

            return normalized.toString();
        } else {
            throw new IllegalArgumentException("Consul service ids must not be empty, must start with a letter, end with a letter or digit, and have as interior characters only letters, digits, and hyphen");
        }
    }
}