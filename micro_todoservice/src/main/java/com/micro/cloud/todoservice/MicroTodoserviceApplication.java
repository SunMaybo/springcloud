package com.micro.cloud.todoservice;

import com.micro.cloud.todoservice.todos.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
@RestController
public class MicroTodoserviceApplication {

	@Autowired
	private Dao<Map<String,Object>> todosDao;

	public static void main(String[] args) {
		SpringApplication.run(MicroTodoserviceApplication.class, args);
	}

	@RequestMapping("/v2/todos")
    public List<Map<String,Object>>todos(){
		return todosDao.findAll();
	}

}
