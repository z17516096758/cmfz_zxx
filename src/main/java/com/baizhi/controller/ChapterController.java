package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.IOUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Encoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @RequestMapping("showAll")
    public Map<String ,Object> showAll(Integer page , Integer rows , String albumId){
        return chapterService.findAllByPages(page, rows, albumId);
    }
    @RequestMapping("edit")
    public String edit(String oper , Chapter chapter,String albumId){
        String uid = null;
        if(oper.equals("add")){
            chapter.setAlbumId(albumId);
            uid = chapterService.add(chapter);
        }
        if(oper.equals("eidt")){
            chapterService.update(chapter);
        }
        if(oper.equals("del")){
            chapterService.delete(chapter.getId(),albumId);
        }
        return uid;
    }
    @RequestMapping("upload")
    public void upload(MultipartFile name , HttpServletRequest request,String id){
        String realPath = request.getSession().getServletContext().getRealPath("/album/chapter/music");
        String fileName = name.getOriginalFilename();
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        try {
            name.transferTo(new File(realPath,fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取文件大小
        long size = name.getSize();
        DecimalFormat format = new DecimalFormat("0.00");
        String str = String.valueOf(size);
        Double dd = Double.valueOf(str)/1024/1024;
        String sizes = format.format(dd)+"MB";
        //获取文件的时长
        AudioFile audioFile = null;
        try {
            audioFile = AudioFileIO.read(new File(realPath, fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AudioHeader audioHeader = audioFile.getAudioHeader();
        int length = audioHeader.getTrackLength();
        String time=length/60+"分"+length%60+"秒";

        Chapter chapter = new Chapter();
        chapter.setId(id);
        chapter.setSize(sizes);
        chapter.setTime(time);
        chapter.setName(fileName);
        chapterService.update(chapter);
    }
    //文件下载
    @RequestMapping("download")
    public void download(String fileName , HttpServletResponse response , HttpServletRequest request){
        //获取文件所在路径
        String realPath = request.getSession().getServletContext().getRealPath("/album/chapter/music");
        //根据路径读取文件
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(realPath, fileName));
            //设置文件的响应格式     响应头                     以附件形式下载
            response.setHeader("content-disposition","attachment;fileName"+ URLEncoder.encode(fileName,"UTF-8"));
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copy(fileInputStream,outputStream);
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
