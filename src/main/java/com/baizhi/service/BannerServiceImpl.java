package com.baizhi.service;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String ,Object> findAllByPages(Integer pageNum , Integer rows) {
        Integer start = (pageNum - 1)*rows;
        Integer totalCount = bannerDao.selectTotal();
        Integer totalPage = totalCount%rows == 0? totalCount/rows:(totalCount/rows)+1;
        Map<String ,Object> map = new HashMap<>();
        map.put("rows",bannerDao.selectAllByPages(start,rows));
        map.put("page",pageNum);
        map.put("records",totalCount);
        map.put("total" , totalPage);
        return map;
    }

    @Override
    public String insert(Banner banner) {
        String id = UUID.randomUUID().toString();
        banner.setId(id).setCreateDate(new Date()).setStatus("冻结");
        bannerDao.insert(banner);
        return id;
    }

    @Override
    public void delete(Banner banner) {
        bannerDao.delete(banner.getId());
    }

    @Override
    public void update(Banner banner) {
        if(banner.getCover().equals("")){
            banner.setCover(null);
        }else if(banner.getCover().contains("C:\\fakepath\\")){
            String p = banner.getCover();
            String[] a = p.split("\\\\");
            String cover = a[a.length-1];
            banner.setCover(cover);
        }
        bannerDao.update(banner);
    }
    public Banner findOne(String id){
        return bannerDao.selectOne(id);
    }
}
