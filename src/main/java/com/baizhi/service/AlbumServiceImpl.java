package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAllByPage(Integer page, Integer rows) {
        Integer start = (page-1)*rows;
        Integer totalCount = albumDao.selectTotal();
        Integer totalPage = totalCount%rows==0?totalCount/rows:(totalCount/rows)+1;
        Map<String ,Object> map = new HashMap<>();
        List<Album> albums = albumDao.selectAllByPages(start, rows);
        map.put("rows",albums);
        map.put("page",page);
        map.put("records",totalCount);
        map.put("total",totalPage);
        return map;
    }

    @Override
    public Album findOne(String id) {
        return albumDao.selectOne(id);
    }

    @Override
    public String add(Album album) {
        String id = UUID.randomUUID().toString();
        album.setId(id).setCreateDate(new Date());
        albumDao.insert(album);
        return id;
    }

    @Override
    public void update(Album album) {
        if(album.getCover().equals("")){
            album.setCover(null);
        }else if(album.getCover().contains("C:\\fakepath\\")){
            String p = album.getCover();
            String[] a = p.split("\\\\");
            String photo = a[a.length-1];
            album.setCover(photo);
        }
        albumDao.update(album);
    }

    @Override
    public void delete(Album album) {
        albumDao.delete(album.getId());
    }
}
