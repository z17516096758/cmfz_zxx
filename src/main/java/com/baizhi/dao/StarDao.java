package com.baizhi.dao;

import com.baizhi.entity.Star;

import java.util.List;

public interface StarDao extends BaseDao<Star> {
    List<Star> selectAll();
}
