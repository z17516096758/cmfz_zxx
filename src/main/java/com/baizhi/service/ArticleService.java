package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {
    Map<String ,Object> findAllByPages(Integer page , Integer rows);
    String add(Article article);
    void update(Article article);
    void delete(String id);
}
