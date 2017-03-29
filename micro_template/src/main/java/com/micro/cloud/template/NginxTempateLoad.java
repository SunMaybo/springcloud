package com.micro.cloud.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maybo on 2016/12/23.
 */
@Component
public class NginxTempateLoad {

    @Autowired
    private DiscoveryClient discoveryClient;


    @Autowired
    private NginxCnfTemplate nginxCnfTemplate;

    @Autowired
    private ApplicationConfig applicationConfig;


    public Map<String,Object>load(Map<String,Object> seviceMap){
        return  nginxCnfTemplate.template(seviceMap);
        //services.serviceInfo("http://192.168.200.23:8500/v1/catalog/service/microTodos");
    }


    public Map<String,Object>serviceInstances(){
        Map<String,Object>serviceMap=new HashMap<String,Object>();
        List<String> services=	discoveryClient.getServices();
        for (String service:services){
            List<ServiceInstance> instanceList = discoveryClient.getInstances(service);
            List<Server>serverList=new ArrayList<Server>();
            if (null==instanceList){
                serviceMap.put(service,null);
            }else{
                for (ServiceInstance serviceInstance:instanceList){
                    Server server=new Server();
                    server.setHost(serviceInstance.getHost());
                    server.setPort(serviceInstance.getPort());
                    server.setServiceId(serviceInstance.getServiceId());
                    serverList.add(server);
                }
                serviceMap.put(service,serverList);
            }
        }
        return serviceMap;
    }

}
