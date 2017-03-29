package com.micro.cloud.todos.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by maybo on 2016/12/21.
 */
//@FeignClient("db")
public interface DbService {

    @RequestMapping(value="/todos",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> todos();

}
