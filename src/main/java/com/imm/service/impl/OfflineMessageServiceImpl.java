package com.imm.service.impl;

import com.imm.dao.OfflineMessageMapper;
import com.imm.model.po.OfflineMessage;
import com.imm.service.OfflineMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Misnearzhang on 2017/4/13.
 */

@Service("offlineMessageService")
@Transactional
public class OfflineMessageServiceImpl implements OfflineMessageService {

    @Autowired
    OfflineMessageMapper offlineMessageMapper;
    public List<OfflineMessage> getOfflineMessage(String account) {
        return offlineMessageMapper.getOfflineMessageByAccout(account,"100");
    }
}
