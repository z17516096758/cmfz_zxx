package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String ,Object> findAllByPage(Integer page , Integer rows , String starId);
    Map<String ,Object> findAllByPages(Integer page , Integer rows);
    List<User> findAll();
    String add(User user);
    void update(User user);
    void delete(String id);
}
