package com.imm.controller;

import com.google.gson.Gson;
import com.imm.model.User;
import com.imm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Misnearzhang on 2017/2/6.
 */

@Controller
public class IndexAction {
    @Autowired
    UserService userService;


    @RequestMapping("index.htm")
    public void Index(HttpServletRequest request, HttpServletResponse response){
        PrintWriter writer=null;
        Gson gson=new Gson();
        try {
            List<User> userlist=userService.getAllUser();
            writer=response.getWriter();
            writer.print(gson.toJson(userlist));
        }catch (Exception e){
            e.printStackTrace();
            writer.print(e.getCause());
        }finally {
            writer.close();
        }
    }

    @RequestMapping("get.htm")
    public void get(HttpServletRequest request, HttpServletResponse response){
        PrintWriter writer=null;
        try {
            writer=response.getWriter();
            Integer port=(Integer) request.getSession().getAttribute("port");
            writer.print("port:"+port);
        }catch (Exception e){
            e.printStackTrace();
            writer.print(e.getCause());
        }finally {
            writer.close();
        }
    }

    @RequestMapping("set.htm")
    public void set(HttpServletRequest request, HttpServletResponse response){
        PrintWriter writer=null;
        try {
            Integer port=request.getRemotePort();
            request.getSession().setAttribute("port",port);
            writer=response.getWriter();
            writer.print("success");
        }catch (Exception e){
            e.printStackTrace();
            writer.print(e.getCause());
        }finally {
            writer.close();
        }
    }
}
