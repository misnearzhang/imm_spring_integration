package com.imm.service.impl;

import com.imm.dao.OfflineMessageDao;
import com.imm.model.OfflineMessage;
import com.imm.service.OfflineMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Misnearzhang on 2017/3/13.
 */

@Service
@Transactional
public class OfferlineServiceImple implements OfflineMessageService {
    @Autowired
    OfflineMessageDao messageDao;
    public boolean saveMessage(OfflineMessage message) {
        int i=messageDao.insert(message);
        if(i>0){
            return true;
        }
        return false;
    }
}
