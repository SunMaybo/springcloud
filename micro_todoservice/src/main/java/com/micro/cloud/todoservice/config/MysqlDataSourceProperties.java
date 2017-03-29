package com.micro.cloud.todoservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by maybo on 2016/12/19.
 */
@Component
public class MysqlDataSourceProperties {
    private String driverClassName="com.mysql.jdbc.Driver";
    @Value("${micro.db.mysql.ip}")
    private String ip;
    @Value("${micro.db.mysql.port}")
    private int port;
    @Value("${micro.db.mysql.user}")
    private String userName;
    @Value("${micro.db.mysql.password}")
    private String password;
    private String database="micro";

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
}
