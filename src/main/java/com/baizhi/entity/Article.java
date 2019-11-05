package com.baizhi.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class Article {
    private String id;
    private String name;
    private String author;
    private String resume;
    private String content;
    private Date createDate;
}
