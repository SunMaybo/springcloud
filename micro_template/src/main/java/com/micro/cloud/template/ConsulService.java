package com.micro.cloud.template;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;

/**
 * Created by maybo on 2016/12/22.
 */
public class ConsulService implements Services {
    private Logger logger= Logger.getLogger(this.getClass());

    private RestTemplate restTemplate;

    public Map<String, Object> services(String url) {
        Map<String,Object>seviceMap=new HashMap<String,Object>();
        Map<String,Object>serviceNameMap=serviceNames(url+"services");
        Iterator iterator=serviceNameMap.keySet().iterator();
        while(iterator.hasNext()){
            String serviceName=(String)iterator.next();
            if ("admin".equals(serviceName)||"consul".equals(serviceName)){

            }else{

                List<Map>seviceInfoList=serviceInfo(url+"service/"+serviceName);
                seviceMap.put(serviceName,seviceInfoList);
            }
        }
        logger.info(seviceMap);
        return seviceMap;
    }

    private Map<String,Object> serviceNames(String url){
        Map<String, Object> map;
        try {
            map = restTemplate.getForObject(url, Map.class);
            return map;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }

    }

    private List<Map> serviceInfo(String url){
        List<Map>serviceList;
        try {
            serviceList = restTemplate.getForObject(url, List.class);
            return serviceList;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }

    }

}
