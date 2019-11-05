package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @RequestMapping("showAll")
    public Map<String , Object> showAll(Integer page , Integer rows){
        return albumService.findAllByPage(page, rows);
    }
    @RequestMapping("upload")
    public void upload(MultipartFile cover , String id , HttpServletRequest request){
        String realPath = request.getSession().getServletContext().getRealPath("/album/image");
        String fileName = cover.getOriginalFilename();
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        try {
            cover.transferTo(new File(realPath,fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Album album = new Album();
        album.setId(id);
        album.setCover(fileName);
        albumService.update(album);
    }
    @RequestMapping("edit")
    public void edit(String oper , Album album){
        String uid = null;
        if(oper.equals("add")){
            uid = albumService.add(album);
        }
        if(oper.equals("edit")){
            albumService.update(album);
        }
        if(oper.equals("del")){
            albumService.delete(album);
        }
    }
}
