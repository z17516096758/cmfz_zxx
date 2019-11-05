package com.baizhi.controller;

import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("star")
public class StarController {
    @Autowired
    private StarService starService;
    @RequestMapping("showAll")
    @ResponseBody
    public Map<String ,Object> showAll(Integer page , Integer rows){
        return starService.findAllByPages(page,rows);
    }
    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile photo , String id , HttpServletRequest request){
        String fileName = photo.getOriginalFilename();
        String realPath = request.getSession().getServletContext().getRealPath("/star/image");
        System.out.println(id);
        try {
            photo.transferTo(new File(realPath,fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Star star = new Star();
        star.setId(id);
        star.setPhoto(fileName);
        starService.update(star);
    }
    @RequestMapping("edit")
    @ResponseBody
    public String  edit(String oper , Star star){
        System.out.println(star);
        String uid = null;
        if(oper.equals("add")){
            uid = starService.add(star);
        }
        if(oper.equals("edit")){
            starService.update(star);
        }
        if(oper.equals("del")){
            starService.delete(star);
        }
        return uid;
    }
    @RequestMapping("show")
    public void show(HttpServletRequest request, HttpServletResponse response){
        final List<Star> list = starService.findAll();
        StringBuffer sb = new StringBuffer();
        sb.append("<select>");
        list.forEach(star->
                sb.append("<option value=").append(star.getRealname()).append(">").append(star.getRealname()).
                        append("</option>")
                );
        sb.append("</select>");
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
