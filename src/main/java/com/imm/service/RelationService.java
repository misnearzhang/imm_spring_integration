package com.imm.service;

import com.imm.model.po.Friends;

import java.util.List;

/**
 * Created by Misnearzhang on 2017/3/28.
 */
public interface RelationService {
    List<Friends> getFriendList(String account);
}
