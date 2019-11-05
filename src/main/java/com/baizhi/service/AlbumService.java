package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumService {
    Map<String ,Object> findAllByPage(Integer page , Integer rows);
    Album findOne(String id);
    String add(Album album);
    void update(Album album);
    void delete(Album album);
}
