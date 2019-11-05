package com.baizhi.dao;

import com.baizhi.entity.Trend;

import java.util.List;

public interface TrendDao extends BaseDao<Trend> {
    List<Trend> selectAllBySex(String sex);
}
