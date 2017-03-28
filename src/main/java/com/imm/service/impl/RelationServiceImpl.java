package com.imm.service.impl;

import com.imm.dao.RelationMapper;
import com.imm.model.po.Friends;
import com.imm.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Misnearzhang on 2017/3/28.
 */
@Service("relationService")
@Transactional
public class RelationServiceImpl implements RelationService {
    @Autowired
    RelationMapper relationMapper;
    public List<Friends> getFriendList(String account) {
        return relationMapper.findFriendList(account);
    }
}
