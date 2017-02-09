package com.imm.dao;

import com.imm.dao.base.MyBatisRepository;
import com.imm.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Misnearzhang on 2017/2/9.
 */

@Component
@MyBatisRepository
public interface UserDao {

    User getUser(Integer userId);

    List<User> getAllUser();
}
