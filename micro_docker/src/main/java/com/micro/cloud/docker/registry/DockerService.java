package com.micro.cloud.docker.registry;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maybo on 2016/12/26.
 */
@Component
public class DockerService {

    @Autowired
    private DockerSwarmProperties dockerSwarmProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public List<Map>nodes(){
      List<Map> mapList= restTemplate
              .getForObject("http://" + dockerSwarmProperties.getHost() + ":" + dockerSwarmProperties.getPort()+"/nodes", List.class);
        return mapList;
    }
    public List<Map>services(){
        return restTemplate.getForObject("http://" + dockerSwarmProperties.getHost() + ":" + dockerSwarmProperties.getPort()+"/services",List.class);
    }
    public List<Map>containers(String host,int port){
        Gson gson=new Gson();
        Map<String,Object> dataMap=new HashMap<String,Object>();
        dataMap.put("status",new String[]{"running"});
        dataMap.put("label", new String[]{"com.docker.swarm.node.id", "com.docker.swarm.service.name"});
       String filters=(String)gson.toJson(dataMap);
        String url="http://"+host+":"+port+"/containers/json?filters={filters}";
        List<Map>containerMaps=restTemplate.getForObject(url,List.class,filters);
        return containerMaps;
    }

}
