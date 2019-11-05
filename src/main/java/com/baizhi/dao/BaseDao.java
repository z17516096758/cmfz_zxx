package com.baizhi.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDao<T> {
    List<T> selectAllByPages(@Param("start") Integer start, @Param("rows") Integer rows);
    T selectOne(String id);
    void insert(T t);
    void update(T t);
    void delete(String id);
    Integer selectTotal();
}
