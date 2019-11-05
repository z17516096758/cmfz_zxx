package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.sun.net.httpserver.Authenticator;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller("adminController")
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @RequestMapping("login")
    @ResponseBody
    public Map<String , Object> login(Admin admin, String code, HttpServletRequest request){
        Map<String , Object> map = new HashMap<>();
        try{
            adminService.login(admin,code, request);
            map.put("status",true);
        }catch (Exception e){
            map.put("status", false);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @RequestMapping("exit")
    public String exit(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("loginAdmin");
        session.invalidate();
        return "redirect:/login/logins.jsp";
    }
}
