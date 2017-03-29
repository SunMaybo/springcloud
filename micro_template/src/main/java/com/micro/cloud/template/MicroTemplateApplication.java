package com.micro.cloud.template;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class MicroTemplateApplication {

	private Logger logger= Logger.getLogger(this.getClass());

	@Autowired
	private NginxTempateLoad nginxTempateLoad;



	@Autowired
	private SchedulerNginxLoad schedulerNginxLoad;

	public static void main(String[] args) {
		SpringApplication.run(MicroTemplateApplication.class, args);
	}
	@RequestMapping("/nginx/reload")
	public Map nginxReload(){
		Map<String,Object>serviceMap=nginxTempateLoad.serviceInstances();
		Map<String,Object>stateMap= nginxTempateLoad.load(serviceMap);
		String state=(String)stateMap.get("state");
		if (Integer.valueOf(state)>0){
			logger.info(stateMap);
			schedulerNginxLoad.storage(serviceMap);
		}else{
			logger.error(stateMap,new Throwable());
		}
		return stateMap;
	}


}
