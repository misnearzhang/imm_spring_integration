package com.imm.mq;

import com.google.gson.Gson;
import com.imm.model.OfflineMessage;
import com.imm.service.OfflineMessageService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 用于接受 core传来的离线消息 用于存储数据库 Created by Misnearzhang on 2017/3/13. */
@Service("receiverFromCoreServer")
public class ReceiverFromCoreServer {

  private final Logger logger =
      org.apache.logging.log4j.LogManager.getLogger(ReceiverFromCoreServer.class);
  @Autowired OfflineMessageService messageService;

  private Gson gson = new Gson();

  public void onMessage(byte[] msg) throws Exception{
    String message = new String(msg);
    logger.info("receive message:{}", message);
    OfflineMessage ofm = gson.fromJson(message, OfflineMessage.class);
    boolean success = messageService.saveMessage(ofm);
    if (success) {
    } else {
    }
  }
}
