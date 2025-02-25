package com.example.picares.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Picture {
    private Long id;
    private String url;
    private String name;
    private String introduction;
    private String category;
    private String tags;
    private Long picSize;
    private Integer picWidth;
    private Integer picHeight;
    private Double picScale;
    private String picFormat;
    private Long userId;
    private Date createTime;
    private Date editTime;
    private Date updateTime;
    private Integer isDelete;
}