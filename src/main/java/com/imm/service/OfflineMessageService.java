package com.imm.service;

import com.imm.model.po.OfflineMessage;

import java.util.List;

/**
 * Created by Misnearzhang on 2017/4/13.
 */
public interface OfflineMessageService {
    List<OfflineMessage> getOfflineMessage(String account);
}
