package com.imm.controller;

import com.google.gson.Gson;
import com.imm.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户验证
 * Created by Misnearzhang on 2017/3/9.
 */

@Controller
public class UserVerifyAction {
    private final Logger logger = LogManager.getLogger( UserVerifyAction.class );

    @Autowired
    UserService userService;

    /**
     *  core服务器验证用户信息用的
     */
    @RequestMapping("/imm/userVerify.htm")
    public void userVerify(HttpServletRequest request, HttpServletResponse response,String params){
        logger.debug("testLog");
    }


    /**
     * client客户端用户登录
     */
    @RequestMapping("/imm/login.htm")
    public void userLogin(HttpServletRequest request, HttpServletResponse response,String account,String password){
        Gson gson=new Gson();
        PrintWriter printWriter=null;
        Map map=new HashMap();
        try {
            printWriter=response.getWriter();
            boolean isVerify=userService.loginByAccountAndPassword(account,password);
            if(isVerify){
                //用户登录成功 给core推送口令  同时给用户返回口令
                map.put("status","200");
                map.put("desc","OK");
            }else{
                //登录失败  账号或密码错误
                map.put("status","500");
                map.put("desc","userName or password err");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            printWriter.write(gson.toJson(map));
            printWriter.flush();
            printWriter.close();
        }
    }
}
