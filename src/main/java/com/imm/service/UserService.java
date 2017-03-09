package com.imm.service;

import com.imm.model.User;

import java.util.List;

/**
 * Created by Misnearzhang on 2017/2/9.
 */
public interface UserService {
    int addUser(User user);
    public User getUserById(Integer userId);

    List<User> getAllUser();

    boolean validateUserName(String name);

    boolean loginByAccountAndPassword(String account,String password);

    int getMaxId();

    int delete(Integer id);
}
