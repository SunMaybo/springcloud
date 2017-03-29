package com.micro.cloud.todoservice.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by maybo on 2016/12/19.
 */
@Repository
public class TodosDaoImpl implements Dao{
    @Autowired
    private JdbcTemplate primaryJdbcTemplate;
    @Override
    public List findAll() {
            List<Map<String,Object>> todosList= primaryJdbcTemplate.queryForList("select * from Todos");
            return  todosList;
    }
}
