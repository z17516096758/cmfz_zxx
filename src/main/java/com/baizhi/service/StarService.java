package com.baizhi.service;

import com.baizhi.entity.Star;

import java.util.List;
import java.util.Map;

public interface StarService {
    Map<String,Object> findAllByPages(Integer page , Integer rows);
    String add(Star star);
    void update(Star star);
    void delete(Star star);
    List<Star> findAll();
}
