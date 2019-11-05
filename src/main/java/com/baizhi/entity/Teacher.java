package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@ExcelTarget(value = "teacher")
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @ExcelIgnore
    private String id;
    @Excel(name = "老师姓名",needMerge = true)
    private String name;
    @ExcelCollection(name="学生")
    private List<Student> students;


}
