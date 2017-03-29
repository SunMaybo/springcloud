package com.micro.cloud.db.config.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigServer
@RestController
@EnableSwagger2
public class MicroDbConfigCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroDbConfigCenterApplication.class, args);
	}
	@RequestMapping("/")
	public String test(){
		return "Hello World!";
	}
}
