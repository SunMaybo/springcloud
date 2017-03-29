package com.micro.cloud.docker.registry;

import java.util.List;

/**
 * Created by maybo on 2016/12/26.
 */
public class Service {

    private String serviceId;
    private String host;
    private String serviceName;
    private int port;
    private List<String>tags;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getPort() {
        return port;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getHost() {
        return host;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }
}
