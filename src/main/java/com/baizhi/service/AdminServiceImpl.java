package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    public void login(Admin admin, String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(admin);
        String inputCode = (String) session.getAttribute("inputCode");
        Admin admin1 = adminDao.selectOne(admin.getUsername());
        if(code.equalsIgnoreCase(inputCode)){
            if(admin1==null){
                throw new RuntimeException("用户名不存在");
            }else{
                if(!admin1.getPassword().equals(admin.getPassword())){
                    throw new RuntimeException("密码不正确");
                }else{
                    session.setAttribute("loginAdmin",admin.getUsername());
                }
            }
        }else{
            throw new RuntimeException("验证码错误");
        }


    }
}
