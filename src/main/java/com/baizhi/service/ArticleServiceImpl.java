package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    private ArticleDao articleDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAllByPages(Integer page, Integer rows) {
        Integer start = (page-1)*rows;
        Integer totalCount = articleDao.selectTotal();
        Integer totalPages = totalCount%rows==0?totalCount/rows:(totalCount/rows)+1;
        List<Article> list = articleDao.selectAllByPages(start,rows);
        Map<String ,Object> map = new HashMap<>();
        map.put("rows",list) ;
        map.put("page",page);
        map.put("records",totalCount);
        map.put("total",totalPages);
        return map;
    }

    @Override
    public String add(Article article) {
        String id = UUID.randomUUID().toString();
        article.setId(id).setCreateDate(new Date());
        articleDao.insert(article);

        return id;
    }

    @Override
    public void update(Article article) {
        articleDao.update(article);
    }

    @Override
    public void delete(String id) {
        articleDao.delete(id);
    }
}
