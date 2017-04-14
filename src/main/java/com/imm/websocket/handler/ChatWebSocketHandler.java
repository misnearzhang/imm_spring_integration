package com.imm.websocket.handler;

import com.google.gson.Gson;
import com.imm.model.protoc.Header;
import com.imm.model.protoc.Message;
import com.imm.model.protoc.MessageEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Misnearzhang on 2017/3/15.
 */

public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final Logger logger = LogManager.getLogger( ChatWebSocketHandler.class );
    private final static List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<WebSocketSession>());
    //接收文本消息，并发送出去
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Gson gson=new Gson();
        Message rece_mesage = gson.fromJson(message.toString(), Message.class);
        Header header = rece_mesage.getHead();
        String type = header.getType();
        switch (type){
            case MessageEnum.TYPE_SYSTEM:
                session.sendMessage(new TextMessage("收到"));
                break;
            case MessageEnum.TYPE_USER:
                session.sendMessage(new TextMessage("收到"));
                break;
            case MessageEnum.TYPE_HANDSHAKE:
                session.sendMessage(new TextMessage("收到"));
                break;
            default:
                session.sendMessage(new TextMessage("消息解码异常请刷新后使用"));
                session.close();
        }
        logger.info(message);
        super.handleTextMessage(session, message);
    }
    //连接建立后处理
    @SuppressWarnings("unchecked")
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("connect to the websocket chat success......");
        sessions.add(session);
        //处理离线消息
    }
    //抛出异常时处理
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        logger.debug("websocket chat connection closed......");
        sessions.remove(session);
    }
    //连接关闭后处理
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket chat connection closed......");
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}