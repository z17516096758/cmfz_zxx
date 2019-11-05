package com.baizhi.service;

import com.baizhi.dao.StarDao;
import com.baizhi.entity.Star;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class StarServiceImpl implements StarService {
    @Autowired
    private StarDao starDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAllByPages(Integer page, Integer rows) {
        Integer start = (page-1)*rows;
        List<Star> stars = starDao.selectAllByPages(start, rows);
        Integer totalCount = starDao.selectTotal();
        Integer totalPage = totalCount%rows==0?totalCount/rows:(totalCount/rows)+1;
        Map<String, Object> map = new HashMap<>();
        map.put("rows",stars);
        map.put("page",page);
        map.put("records",totalCount);
        map.put("total",totalPage);
        return map;
    }

    @Override
    public String add(Star star) {
        String id = UUID.randomUUID().toString();
        star.setId(id);
        starDao.insert(star);
        return id;
    }

    @Override
    public void update(Star star) {
        if(star.getPhoto().equals("")){
            star.setPhoto(null);
        }else if(star.getPhoto().contains("C:\\fakepath\\")){
            String p = star.getPhoto();
            String[] a = p.split("\\\\");
            String photo = a[a.length-1];
            star.setPhoto(photo);
        }
        starDao.update(star);
    }

    @Override
    public void delete(Star star) {
        starDao.delete(star.getId());
    }

    @Override
    public List<Star> findAll() {
        return starDao.selectAll();
    }
}
