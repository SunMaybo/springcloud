package com.micro.cloud.docker.registry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by maybo on 2016/12/26.
 */
@Component
public class DockerSwarmProperties {
    @Value("${docker.swarm.host:127.0.0.1}")
    private String host;
    @Value("${docker.swarm.port:2375}")
    private int port;

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }
}
