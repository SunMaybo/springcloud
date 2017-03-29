package com.micro.cloud.todos.client;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by maybo on 2016/12/21.
 */
//@FeignClient("microTodos")
public interface TodosService {
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value = "/api/todos",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Object> todos(@RequestBody List<ToDos> toDos);
}
