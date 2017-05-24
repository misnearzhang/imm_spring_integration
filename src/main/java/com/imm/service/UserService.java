package com.imm.service;

import com.imm.model.po.User;

import java.util.List;

/**
 * Created by Misnearzhang on 2017/2/9.
 */
public interface UserService {
    int addUser(User user);
    public User getUserById(Integer userId);

    List<User> getAllUser();

    int delete(Integer id);

    boolean loginByAccountAndPassword(String account, String password);

    User getByAccount(String account);
}
