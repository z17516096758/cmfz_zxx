package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAllByPage(Integer page, Integer rows, String starId) {
        Integer start =(page-1)*rows;
        Integer totalCount = userDao.selectTotal();
        Integer totalPage = totalCount%rows==0?totalCount/rows:totalCount/rows+1;
        Map<String , Object> map = new HashMap<>();
        map.put("rows",userDao.selectAllByPage(start,rows,starId));
        map.put("page",page);
        map.put("total",totalPage);
        map.put("records",totalCount);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAllByPages(Integer page, Integer rows) {
        Integer start =(page-1)*rows;
        Integer totalCount = userDao.selectTotal();
        Integer totalPage = totalCount%rows==0?totalCount/rows:totalCount/rows+1;
        Map<String , Object> map = new HashMap<>();
        map.put("rows",userDao.selectAllByPages(start,rows));
        map.put("page",page);
        map.put("total",totalPage);
        map.put("records",totalCount);
        return map;
    }

    @Override
    public List<User> findAll() {

        return userDao.selectAll();
    }

    @Override
    public String add(User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id).setCreateDate(new Date());
        userDao.insert(user);
        return id;
    }

    @Override
    public void update(User user) {
        if(user.getPhoto().equals("")){
            user.setPhoto(null);
        }
        userDao.update(user);
    }

    @Override
    public void delete(String id) {
        userDao.delete(id);
    }

}
