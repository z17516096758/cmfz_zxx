package com.baizhi.service;

import com.baizhi.dao.TrendDao;
import com.baizhi.entity.Trend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TrendServiceImpl implements TrendService {
    @Autowired
    private TrendDao trendDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAllBySex() {
        List<Trend> boys = trendDao.selectAllBySex("男");
        List<Trend> girls = trendDao.selectAllBySex("女");
        List<Integer> boy = new ArrayList<>();
        List<Integer> girl = new ArrayList<>();
        for(int i =0 ; i<12 ; i++){
            boy.add(0);
            girl.add(0);
        }
        String[] month = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        for (int i = 0; i < month.length; i++) {
            for (int j = 0; j < boys.size(); j++) {
                if(boys.get(j).getMonth().equals(month[i])){
                    boy.remove(i);
                    boy.add(i,boys.get(j).getCount());
                }
            }
            for (int j = 0; j < girls.size(); j++) {
                if(girls.get(j).getMonth().equals(month[i])){
                    girl.remove(i);
                    girl.add(i,girls.get(j).getCount());
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("boys",boy);
        map.put("grils",girl);
        return map;
    }
}
