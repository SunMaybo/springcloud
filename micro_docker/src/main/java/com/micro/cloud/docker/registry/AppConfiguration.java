package com.micro.cloud.docker.registry;

import com.ecwid.consul.v1.ConsulClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by maybo on 2016/12/27.
 */
@Configuration
public class AppConfiguration {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean(name="consulClient")
   public ConsulClient consulClient(){
        return new ConsulClient(applicationProperties.getHost(), applicationProperties.getPort());
    }

    @Bean(name="registerService")
   public RegisterService registerService(){
        return new ConsulRegisterService();
    }
    @Bean(name="dockerService")
    public DockerService dockerService(){
        return new DockerService();
    }

    @Bean
    public DockerConsistentService dockerConsistentService(){
        return new DockerConsistentService();
    }
}
