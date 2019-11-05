package com.baizhi.controller;

import com.baizhi.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("trend")
public class TrendController {
    @Autowired
    private TrendService trendService;
    @RequestMapping("show")
    public Map<String ,Object> show(){
        return trendService.findAllBySex();
    }
}
