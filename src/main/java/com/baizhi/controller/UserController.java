package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("showAll")
    public Map<String,Object> showAll(Integer page , Integer rows , String userId){
        return userService.findAllByPage(page, rows, userId);
    }
    @RequestMapping("showAllByPages")
    public Map<String,Object> showAllByPages(Integer page , Integer rows){
        return userService.findAllByPages(page, rows);
    }
    @RequestMapping("edit")
    public String  edit(String oper , User user){
        String uid = null;
        if(oper.equals("add")){
            uid = userService.add(user);
        }
        if(oper.equals("edit")){
            userService.update(user);
        }
        if(oper.equals("del")){
            userService.delete(user.getId());
        }
        return uid;
    }
    @RequestMapping("upload")
    public void upload(MultipartFile photo , String id , HttpServletRequest request){
        String fileName = photo.getOriginalFilename();
        String name = new Date().getTime()+"-"+fileName;
        String realPath = request.getSession().getServletContext().getRealPath("/user/image");
        try {
            photo.transferTo(new File(realPath,name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setId(id);
        user.setPhoto(name);
        userService.update(user);
    }
    @RequestMapping("exportUser")
    public void exportUser(HttpServletResponse response){

        List<User> users = userService.findAll();
        users.forEach(user -> user.setPhoto("E:/后期项目/图片专用/"+user.getPhoto()));
        String fileName = "用户报表("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+").xls";
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息","用户信息表"),User.class,users);
        try {
            fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
            //设置 response
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-disposition","attachment;filename="+fileName);
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
