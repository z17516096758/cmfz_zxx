package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ExcelTarget(value = "1")
public class Student {
    @Excel(name="ID",height = 20,width=30)
    private String id;
    @Excel(name="名字")
    private String name;
    @Excel(name = "年龄")
    private int age;
    @Excel(name = "生日" , format = "yyyy年mm月dd日",width=20)
    private Date bir;
    @Excel(name="头像",type = 2,width = 40,height = 20,imageType = 1)
    private String photo;
}
