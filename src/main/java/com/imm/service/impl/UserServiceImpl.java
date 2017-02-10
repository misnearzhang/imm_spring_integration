package com.imm.service.impl;

import com.imm.dao.UserDao;
import com.imm.model.User;
import com.imm.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/** Created by Misnearzhang on 2017/2/9. */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
  @Resource private UserDao userDao;

  public int addUser(User user) {
    return userDao.insert(user);
  }

  public User getUserById(Integer userId) {
    return userDao.selectByPrimaryKey(userId);
  }

  public List<User> getAllUser() {
    return userDao.listAll();
  }

  public boolean validateUserName(String name) {
    int count = userDao.findCountName(name);
    if (count == 0) {
      return true;
    } else {
      return false;
    }
  }

    public int getMaxId() {
        return userDao.findMaxId();
    }

    public int delete(Integer id) {
        return userDao.deleteByPrimaryKey(id);
    }
}
