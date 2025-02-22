package com.example.picares.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVO {
    private Long id;
    private String userAccount;
    private String userName;
    private String userAvatar;
    private String userProfile;
    private String userRole;
    private Date createTime;
}
