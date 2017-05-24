package com.imm.service;

import com.imm.model.po.Synch;

import java.util.List;

/**
 * Created by misnearzhang on 2017/5/24.
 */
public interface SynchService {
    /**
     * 获取同步事件组
     * @param account
     * @return
     */
    List<Synch> getSynchEvents(String account);
}
