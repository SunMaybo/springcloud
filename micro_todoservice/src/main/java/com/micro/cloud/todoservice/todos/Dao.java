package com.micro.cloud.todoservice.todos;

import java.util.List;

/**
 * Created by maybo on 2016/12/19.
 */
public interface Dao<T> {

    public List<T>findAll();
}
