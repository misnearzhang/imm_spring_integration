package com.imm.controller;

import com.google.gson.Gson;
import com.imm.model.User;
import com.imm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/** Created by Misnearzhang on 2017/2/6. */
@Controller
public class IndexAction {
  @Autowired UserService userService;

  @RequestMapping("list.htm")
  public ModelAndView Index(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView mv = new ModelAndView("/admin/user_list.html");
    try {
      List<User> userList = userService.getAllUser();
      if (userList != null && userList.size() > 0) {
        mv.addObject("users", userList);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return mv;
  }

  @RequestMapping("get.htm")
  public void get(HttpServletRequest request, HttpServletResponse response) {
    PrintWriter writer = null;
    try {
      writer = response.getWriter();
      Integer port = (Integer) request.getSession().getAttribute("port");
      writer.print("port:" + port);
    } catch (Exception e) {
      e.printStackTrace();
      writer.print(e.getCause());
    } finally {
      writer.close();
    }
  }

  @RequestMapping("set.htm")
  public void set(HttpServletRequest request, HttpServletResponse response) {
    PrintWriter writer = null;
    try {
      Integer port = request.getRemotePort();
      request.getSession().setAttribute("port", port);
      writer = response.getWriter();
      writer.print("success");
    } catch (Exception e) {
      e.printStackTrace();
      writer.print(e.getCause());
    } finally {
      writer.close();
    }
  }

  @RequestMapping("add.htm")
  public void AddUser(HttpServletRequest request, HttpServletResponse response, String userName) {
    PrintWriter writer = null;
    User user = new User();
    user.setName(userName);
    try {
      writer = response.getWriter();
      boolean is = userService.validateUserName(userName);
      if (is) {
        int result = userService.addUser(user);
        int max = userService.getMaxId();
        writer.print(max);
      } else {
        writer.print("-1");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      writer.close();
    }
  }

  @RequestMapping("delete.htm")
  public void delete(HttpServletRequest request, HttpServletResponse response, String id) {
    try {
      int i = 0;
      if (id != null && !"".equals(id)) {
        i = Integer.parseInt(id);
      }
      userService.delete(i);
        response.sendRedirect("/list.htm");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
