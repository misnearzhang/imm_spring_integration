package com.imm.controller;

import com.imm.model.po.OfflineMessage;
import com.imm.model.po.User;
import com.imm.service.OfflineMessageService;
import com.imm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Misnearzhang on 2017/2/6.
 */
@Controller
public class IndexAction {

    @Value("${exchange-from-core-server}")
    String exchange;
    @Value("${rout-from-core-server}")
    String route;
    @Value("${queue-from-core-server}")
    String queue;

    @Autowired
    UserService userService;
    @Autowired
    OfflineMessageService offlineMessageService;

  /*
  mq sender
   */
    //@Autowired AmqpTemplate amqpTemplate;

    @RequestMapping("list.htm")
    public ModelAndView Index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/view/user_list.html");
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

    /* @RequestMapping("add.htm")
     public void AddUser(HttpServletRequest request, HttpServletResponse response, String userName) {
       PrintWriter writer = null;
       try {
         writer = response.getWriter();
         boolean is = userService.validateUserName(userName);
         if (is) {
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
   */
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

    @RequestMapping("edit.htm")
    public ModelAndView Edit(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/view/admin/admin_editor.html");
        try {
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return mv;
    }

    /*@RequestMapping("addMessage.htm")
    public ModelAndView addMessage(HttpServletRequest request, HttpServletResponse response) {
      OfflineMessage message1 = new OfflineMessage();
      message1.setMessageFrom(12);
      message1.setMessageTo(34);
      message1.setMessageStatus("1");
      message1.setAddtime(new Date());
      message1.setUpdatetime(new Date());
      message1.setMessageContent("one two three four five six seven eight nine ten");
      offlineMessageService.saveMessage(message1);

      ModelAndView mv = new ModelAndView("/view/user_list.html");
      try {

      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
      return mv;
    }
  */
    @RequestMapping("test")
    public void getUserMessage(HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping("/imm/uploadImg.htm")
    public void uploadImg() {
        //对外提供服务
    }

    @RequestMapping("/imm/validateUser.htm")
    public void validateUser() {
        //验证用户  提供用户登录验证 然后发送消息给core
    }

    @RequestMapping("/imm/getOfflineMessage")
    @ResponseBody
    public Object getOfflineMessage(HttpServletRequest request, HttpServletResponse response, String account) {
        //获取用户的离线消息
        List<OfflineMessage> list=offlineMessageService.getOfflineMessage(account);
        return list;
    }

    @RequestMapping("sendMQ")
    public void sendMQ(HttpServletRequest request, HttpServletResponse response) {
        String message = request.getParameter("value");
        //amqpTemplate.convertAndSend(exchange,route,message.getBytes());
    }
}
