package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ExcelTarget(value = "user")
public class User {
    @Excel(name="ID")
    private String id;
    @Excel(name="用户名")
    private String username;
    @Excel(name = "密码")
    private String password;
    @Excel(name = "盐值")
    private String salt;
    @Excel(name = "昵称")
    private String nickname;
    @Excel(name="电话")
    private String phone;
    @Excel(name = "省份")
    private String province;
    @Excel(name = "城市")
    private String city;
    @Excel(name="标签")
    private String sign;
    @Excel(name = "头像",type = 2,width = 40,height = 20,imageType = 1)
    private String photo;
    @Excel(name = "性别")
    private String sex;
    @JSONField(format = "YYYY-MM-DD")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建日期" ,format = "yyyy年MM月dd日")
    private Date createDate;
    @Excel(name = "所粉明星")
    private String starId;

}
