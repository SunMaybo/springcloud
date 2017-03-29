package com.micro.cloud.template;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by maybo on 2016/12/22.
 */
@Component
public class ApplicationConfig {

    @Value("${spring.cloud.consul.host}")
    private String host;

    @Value("${spring.cloud.consul.port:8500}")
    private int port;

    @Value("${spring.cloud.consul.internal:6000}")
    private long internal;

    @Value("${spring.cloud.consul.refresh:true}")
    private boolean refresh;

    public boolean isRefresh() {
        return refresh;
    }

    public int getPort() {
        return port;
    }

    public long getInternal() {
        return internal;
    }

    public String getHost() {
        return host;
    }

}
