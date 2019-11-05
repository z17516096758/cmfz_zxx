package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    Map<String,Object> findAllByPages(Integer page , Integer rows , String albumId);
    String add(Chapter chapter);
    void update(Chapter chapter);
    void delete(String id,String albumId);
}
