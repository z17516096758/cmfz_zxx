package com.baizhi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.baizhi.util.SecurityCodeUtil.generateVerifyCode;
import static com.baizhi.util.SecurityCodeUtil.getImage;

@Controller
@RequestMapping("picture")
public class PictureController {
    @RequestMapping("getPicture")
    public String getPicture(HttpServletRequest request , HttpServletResponse response){
        String generateVerifyCode = generateVerifyCode(4);
        HttpSession session = request.getSession();
        session.setAttribute("inputCode",generateVerifyCode);
        //生成图片(宽度，高度，随即字符)
        BufferedImage image = getImage(255, 80, generateVerifyCode);
        //将生成的验证码图片写成流
        ServletOutputStream stream = null;
        try{
            stream = response.getOutputStream();
            ImageIO.write(image, "png", stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
