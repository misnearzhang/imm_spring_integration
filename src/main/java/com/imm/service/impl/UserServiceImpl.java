package com.imm.service.impl;

import com.imm.dao.UserDao;
import com.imm.model.User;
import com.imm.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Misnearzhang on 2017/2/9.
 */


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    public User getUserById(Integer userId) {
        return userDao.selectByPrimaryKey(userId);
    }

}
