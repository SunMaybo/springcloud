package com.micro.cloud.template;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by maybo on 2016/12/23.
 */
@Component@Scope("prototype")
public class SchedulerNginxLoad {


private Logger logger=Logger.getLogger(this.getClass());
    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private NginxTempateLoad nginxTempateLoad;

    @Autowired
    private DiscoveryClient discoveryClient;

    private static String SERVICE_STORAGE_FILE=System.getProperty("user.dir")+"/services.bat";

  private Map<String,Object> serviceMap;

    public synchronized void storage(Map<String, Object> serviceMap) {
        this.serviceMap = serviceMap;
        FileUtils.writeObject(serviceMap,SERVICE_STORAGE_FILE);
    }

    @PostConstruct
    public void init(){
        logger.info(SERVICE_STORAGE_FILE);
        Object obj=FileUtils.readObject(SERVICE_STORAGE_FILE);
        if (null!=obj) {
            Map<String, Object> dataMap = (Map) obj;
            serviceMap=dataMap;
        }
        if(applicationConfig.isRefresh()){
            Timer timer=new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                   Map<String,Object>newl=nginxTempateLoad.serviceInstances();

                    if (isUpdate(serviceMap,newl)){
                       Map<String,Object> stateMap=nginxTempateLoad.load(newl);
                        String state=(String)stateMap.get("state");
                        if (Integer.valueOf(state)>0){
                            logger.info(stateMap);
                            storage(newl);
                        }else{
                            logger.error(stateMap,new Throwable());
                        }
                    }

                }
            }, 0,applicationConfig.getInternal());
        }
    }

    private boolean isUpdate(Map<String,Object>old,Map<String,Object> newl){
       if (null==newl||newl.isEmpty()){
           return false;
       }
        if (null==old||old.isEmpty()){
            return true;
        }
        if (old.keySet().size()!=newl.keySet().size()){
            return true;
        }else{
            Set<String>keys=old.keySet();
            Iterator iterator=keys.iterator();
            while(iterator.hasNext()){
                String key=(String)iterator.next();
                List<Server>oldList=(List<Server>)old.get(key);
                List<Server>newList=(List<Server>)newl.get(key);
                if (null==newList){
                    return true;
                }else{
                    if (oldList.size()!=newList.size()){
                        return true;
                    }else{
                        for(int i=0;i<oldList.size();i++){
                            if (!oldList.get(i).getHost().equals(newList.get(i).getHost())||oldList.get(i).getPort()!=newList.get(i).getPort()){
                                return true;
                            }
                        }
                    }
                }
            }
        }
       return false;
    }

}
