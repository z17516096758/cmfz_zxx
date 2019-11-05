package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService{
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private AlbumDao albumDao;
    @Override
    public Map<String, Object> findAllByPages(Integer page, Integer rows, String albumId) {
        Integer start = (page-1)*rows;
        Integer totalCount = chapterDao.selectTotal();
        Integer totalPages = totalCount%rows==0?totalCount/rows:(totalCount/rows)+1;
        List<Chapter> list = chapterDao.selectAllByPages(start,rows,albumId);
        Map<String , Object> map = new HashMap<>();
        map.put("rows",list);
        map.put("page",page);
        map.put("records",totalCount);
        map.put("total",totalPages);
        return map;
    }

    @Override
    public String add(Chapter chapter) {
        String id = UUID.randomUUID().toString();
        chapter.setId(id).setCreateDate(new Date());
        Album album = albumDao.selectOne(chapter.getAlbumId());
        album.setCount(album.getCount()+1);
        albumDao.update(album);
        chapterDao.insert(chapter);
        return id;
    }

    @Override
    public void update(Chapter chapter) {
        chapterDao.update(chapter);
    }

    @Override
    public void delete(String id , String albumId) {
        Album album = albumDao.selectOne(albumId);
        album.setCount(album.getCount()-1);
        albumDao.update(album);
        chapterDao.delete(id);
    }
}
