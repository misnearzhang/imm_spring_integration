package com.imm.websocket.config;

import com.imm.websocket.handler.ChatWebSocketHandler;
import com.imm.websocket.interceptor.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by Misnearzhang on 2017/3/15.
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        //WebIM WebSocket通道
        registry.addHandler(new ChatWebSocketHandler(),"/connection.htm").addInterceptors(new WebSocketHandshakeInterceptor());
        //registry.addHandler(chatWebSocketHandler, "/sockjs/webSocketIMServer").setAllowedOrigins("localhost").addInterceptors(myInterceptor()).withSockJS();
    }
}