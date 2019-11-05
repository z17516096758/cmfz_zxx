package com.baizhi.entity;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
public class Admin {
    private String id ;
    private String username;
    private String password;
}
