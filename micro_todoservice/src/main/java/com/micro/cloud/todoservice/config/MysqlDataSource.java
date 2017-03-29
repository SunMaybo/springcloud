package com.micro.cloud.todoservice.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by maybo on 2016/12/19.
 */
@Configuration
public class MysqlDataSource {

    private Logger logger=Logger.getLogger(this.getClass());
    @Autowired
    private MysqlDataSourceProperties mysqlDataSourceProperties;
    @Bean(name="primaryDataSource")
    public DataSource primaryDataSource(){
        DataSourceBuilder builder= DataSourceBuilder.create();
        builder.driverClassName(mysqlDataSourceProperties.getDriverClassName());
        builder.password(mysqlDataSourceProperties.getPassword());
        builder.url("jdbc:mysql://" + mysqlDataSourceProperties.getIp() + ":" + mysqlDataSourceProperties.getPort() + "/" + mysqlDataSourceProperties.getDatabase());
        builder.username(mysqlDataSourceProperties.getUserName());
        DataSource dataSource=builder.build();
        return dataSource;

    }
    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(
            @Qualifier("primaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
