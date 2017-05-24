package com.imm.controller;

import com.imm.model.po.Synch;
import com.imm.service.SynchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by misnearzhang on 2017/5/24.
 */
@Controller
public class SynchAction {
    @Autowired
    private SynchService synchService;
    @RequestMapping("/imm/getTransactionEventList.htm")
    @ResponseBody
    public Object freshFriendList(String account){
        List<Synch> synchList = synchService.getSynchEvents(account);
        return synchList;
    }
}
