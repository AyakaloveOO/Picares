package com.example.picares.model.vo;

import lombok.Data;

@Data
public class LoginUserVO {
    private Long id;
    private String userAccount;
    private String userName;
    private String userAvatar;
    private String userProfile;
    private String userRole;
}
