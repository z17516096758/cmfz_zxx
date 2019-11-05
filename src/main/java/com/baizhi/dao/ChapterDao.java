package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao extends BaseDao<Chapter> {
    List<Chapter> selectAllByPages(@Param("start") Integer start, @Param("rows") Integer rows, @Param("albumId") String albumId);
}
