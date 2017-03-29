package com.micro.cloud.todos.client;

/**
 * Created by maybo on 2016/12/21.
 */
public class ToDos {
    private String name;
    private String group;

    public void setGroup(String group) {
        this.group = group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }
}
