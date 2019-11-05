package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.Map;

public interface BannerService {
    Map<String ,Object> findAllByPages(Integer pageNum , Integer rows);
    String insert(Banner banner);
    void delete(Banner banner);
    void update(Banner banner);
    Banner findOne(String id);
}
