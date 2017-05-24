package com.imm.service.impl;

import com.imm.dao.UserMapper;
import com.imm.model.po.User;
import com.imm.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/** Created by Misnearzhang on 2017/2/9. */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
  @Resource private UserMapper userDao;

  public int addUser(User user) {
    return userDao.insert(user);
  }

  public User getUserById(Integer userId) {
    return userDao.selectByPrimaryKey(userId);
  }

  public List<User> getAllUser() {
    return userDao.listAll();
  }

  public int delete(Integer id) {
        return userDao.deleteByPrimaryKey(id);
    }

  public boolean loginByAccountAndPassword(String userAccount, String password) {
    User user=userDao.checkUser(userAccount,password);
    if(user!=null){
      return true;
    }
    return false;
  }

  @Override
  public User getByAccount(String account) {
    return userDao.findByAccount(account);
  }

}
