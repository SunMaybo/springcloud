package com.micro.cloud.docker.registry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by maybo on 2016/12/26.
 */
@Component
public class ApplicationProperties {
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

    public void setHost(String host) {
        this.host = host;
    }

    public void setInternal(long internal) {
        this.internal = internal;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }
}
