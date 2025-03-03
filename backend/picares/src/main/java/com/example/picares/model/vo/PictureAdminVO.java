package com.example.picares.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class PictureAdminVO {
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
    private String userAccount;
    private String userAvatar;
    private Date createTime;
}
