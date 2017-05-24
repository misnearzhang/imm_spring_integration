package com.imm.service.impl;

import com.imm.dao.SynchMapper;
import com.imm.model.po.Synch;
import com.imm.service.SynchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by misnearzhang on 2017/5/24.
 */
@Service("synchService")
public class SynchServiceImpl implements SynchService{
    @Autowired
    private SynchMapper synchMapper;
    @Override
    public List<Synch> getSynchEvents(String account) {
        return synchMapper.getSynchListByAccount(account);
    }
}
