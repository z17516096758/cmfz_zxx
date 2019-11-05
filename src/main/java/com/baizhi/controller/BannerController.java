package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @RequestMapping("showAll")
    public Map<String ,Object> showAll(Integer page ,Integer rows ){
        return bannerService.findAllByPages(page,rows);
    }
    @RequestMapping("upload")
    public void upload(MultipartFile cover , HttpServletRequest request,String id){
        String realPath = request.getSession().getServletContext().getRealPath("/banner/image");
        String fileName = cover.getOriginalFilename();
        try {
            cover.transferTo(new File(realPath,fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Banner banner = new Banner();
        banner.setId(id);
        banner.setCover(fileName);
        bannerService.update(banner);
    }
    @RequestMapping("edit")
    public String edit(Banner banner , String oper){
        String uid = null;
        if(oper.equals("add")){
            uid = bannerService.insert(banner);
        }
        if(oper.equals("edit")){
            bannerService.update(banner);
        }
        if(oper.equals("del")){
            bannerService.delete(banner);
        }
        return uid;
    }
}
