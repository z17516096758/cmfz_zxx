package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @RequestMapping("showAll")
    public Map<String , Object> showAll(Integer page , Integer rows){
        return articleService.findAllByPages(page, rows);
    }
    @RequestMapping("edit")
    public void edit(Article article , String oper){
        if(oper.equals("del")){
            articleService.delete(article.getId());
        }
    }
    @RequestMapping("upload")
    public HashMap<String ,Object> upload(HttpServletRequest request , HttpServletResponse response, MultipartFile photo){
        String realPath = request.getSession().getServletContext().getRealPath("/article/upload");
        String fileName = photo.getOriginalFilename();
        String name = new Date().getTime()+"-"+fileName;
        HashMap<String,Object> map = new HashMap<>();
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        //获取http
        String scheme = request.getScheme();
        //获取localhost(IP)
        String serverName = request.getServerName();
        //获取端口号 8989
        int serverPort = request.getServerPort();
        //获取项目名 cmfz_zxx
        String contextPath = request.getContextPath();
        //网络路径的拼接
        String serverPath = scheme+"://"+serverName+":"+serverPort+"/"+contextPath+"/article/upload/"+name;
        try {
            photo.transferTo(new File(realPath,name));
            map.put("url",serverPath);
            map.put("error",0);
        } catch (IOException e) {
            map.put("error",1);
            map.put("url","上传失败");
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping("queryAllPhoto")
    public HashMap<String ,Object> queryAllPhoto(HttpServletRequest request){
        HashMap<String ,Object> maps = new HashMap<>();
        ArrayList<Object> list = new ArrayList<>();
        //获取图片文件夹路径
        String realPath = request.getSession().getServletContext().getRealPath("/article/upload");
        //获取文件夹
        File file = new File(realPath);
        //获取文件夹下所有的文件名称
        String[] names = file.list();
        for(int i = 0; i<names.length;i++){
            //获取文件名字
            String name = names[i];
            HashMap<String,Object> map = new HashMap<>();
            map.put("is_dir",false);
            map.put("has_file",false);
            File file1 = new File(realPath,name);
            map.put("filesize",file1.length());
            map.put("is_photo",true);
            map.put("filetype", FilenameUtils.getExtension(name));
            map.put("filename",name);

            //字符串拆分  152213213-下载.jpg
            String[] split = name.split("-");
            String times = split[0];
            long time = Long.parseLong(times);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
            String format = dateFormat.format(time);
            map.put("datatime",format);
            //
            list.add(map);
        }
        maps.put("current_url","http://localhost:8989/cmfz_zxx/article/upload/");
        maps.put("total_count",list.size());//文件数量
        maps.put("file_list",list);//文件集合

        return maps;
    }
    @RequestMapping("add")
    public void add(Article article){
        articleService.add(article);
    }
    @RequestMapping("update")
    public void update(Article article){
        articleService.update(article);
    }
}
