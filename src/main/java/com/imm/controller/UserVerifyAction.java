package com.imm.controller;

import com.imm.push.PushService;
import com.imm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户验证
 * Created by Misnearzhang on 2017/3/9.
 */

@Controller
public class UserVerifyAction {
    @Autowired
    UserService userService;
    @Autowired
    PushService pushService;


    /**
     *  core服务器验证用户信息用的
     */
    @RequestMapping("/imm/userVerify.htm")
    public void userVerify(){

    }


    /**
     * client客户端用户登录
     */
    @RequestMapping("/imm/userLogin.htm")
    public void userLogin(HttpServletRequest request, HttpServletResponse response,String params){
        //boolean isVerify=userService.loginByAccountAndPassword("","");
        boolean isVerify=true;
        if(isVerify){
            //用户登录成功 给core推送口令  同时给用户返回口令
            if(pushService.isConnected()){
                pushService.send("hello world\r\n");
            }else{
                pushService.connect();
                pushService.send("hello world\r\n");
            }
        }else{
            //登录失败  账号或密码错误
        }
    }
}
