package com.micro.cloud.todos.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableAsync
@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@RestController
public class MicroTodosclientApplication {

	@Autowired
	RestTemplate client;

	/**
	 * LoadBalanced 注解表明restTemplate使用LoadBalancerClient执行请求
	 *
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		RestTemplate template = new RestTemplate();
		SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) template.getRequestFactory();
		factory.setConnectTimeout(3000);
		factory.setReadTimeout(3000);
		return template;
	}


	public static void main(String[] args) {
		SpringApplication.run(MicroTodosclientApplication.class, args);
	}

	@RequestMapping(value = "/todos",method = RequestMethod.GET)
	public Map<String,Object> find(String id){

		Map map=client.getForObject("http://microTodos/api/todos?"+"id="+id,Map.class);
		return map;

	}
	public Map<String,Object> find(List<String> ids,List<String> fields){
		Map map=client.getForObject("http://microTodos/api/todos?" + "ids=" + ids+"&fields="+fields, Map.class);
		return map;

	}

	@RequestMapping(value = "/todos",method = RequestMethod.POST)
	public Map addToDos(String name,String group){

     ToDos toDos=new ToDos();
		toDos.setGroup(group);
		toDos.setName(name);
		List<ToDos>toDosList=new ArrayList<ToDos>();
		toDosList.add(toDos);
			Map<String,Object> map=(Map<String,Object>)client.postForObject("http://microTodos/api/todos", toDosList, Map.class);
		return map;
		//return todosService.todos(toDosList);
	}
	@RequestMapping(value = "/todos",method = RequestMethod.DELETE)
		 public Map<String,Object> deltoDos(@RequestBody String ids){
		HttpHeaders headers = new HttpHeaders();
		MediaType type2 = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type2);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		HttpEntity<String> httpEntity=new HttpEntity<String>(ids.toString(),headers);
		ResponseEntity<Map> responseEntity=client.exchange("http://microTodos/api/todos", HttpMethod.DELETE, httpEntity, Map.class);
		return responseEntity.getBody();
	}

	@RequestMapping(value = "/todos/{id}",method = RequestMethod.PUT)
	public void putDos(@RequestBody Map<String,Object> param,@PathVariable("id") int id){
		client.put("http://microTodos/api/todos/"+id,param);

	}
}
