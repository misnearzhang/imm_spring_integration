package com.imm.model;

/**
 * Created by Misnearzhang on 2017/2/9.
 */
public class User {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }
}
