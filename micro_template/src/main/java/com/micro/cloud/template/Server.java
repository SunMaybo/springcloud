package com.micro.cloud.template;

import java.io.Serializable;

/**
 * Created by maybo on 2016/12/23.
 */
    public class Server implements Serializable {

    private String host;
    private int port;
    private String serviceId;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public String getServiceId() {
        return serviceId;
    }
}
