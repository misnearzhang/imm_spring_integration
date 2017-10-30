package com.imm.controller;

import com.google.gson.Gson;
import com.imm.model.po.Friends;
import com.imm.model.po.User;
import com.imm.service.RelationService;
import com.imm.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    RelationService relationService;
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
    @ResponseBody
    public Object userLogin(HttpServletRequest request, HttpServletResponse response,String account,String password){
        Map map=new HashMap();
        try {
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
            map.put("status","500");
            map.put("desc","内部错误,请稍后再试");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * client客户端用户登录
     */
    @RequestMapping("/imm/getFriendsList.htm")
    @ResponseBody
    public Object getFriends(HttpServletRequest request, HttpServletResponse response,String account,String password){
        Map map=new HashMap();
        try {
            List<Friends> list=relationService.getFriendList(account);
            if(list!=null&&list.size()>0){
                //用户登录成功 给core推送口令  同时给用户返回口令
                map.put("status","200");
                map.put("desc","OK");
                map.put("data",list);
            }else{
                //登录失败  账号或密码错误
                map.put("status","404");
                map.put("desc","你没有朋友");
            }
        }catch (Exception e){
            map.put("status",500);
            map.put("desc","内部错误,请稍后再试");
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping("/imm/FreshFriendList.htm")
    @ResponseBody
    public Object freshFriendList(String account){
        User user = userService.getByAccount(account);
        return user;
    }
}
