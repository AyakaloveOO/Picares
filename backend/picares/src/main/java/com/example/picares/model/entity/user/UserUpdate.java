package com.example.picares.model.entity.user;

import com.example.picares.common.ErrorCode;
import com.example.picares.common.ThrowUtils;
import com.example.picares.model.enums.UserEnums;
import lombok.Data;

@Data
public class UserUpdate {
    private Long id;
    private String userAccount;
    private String userName;
    private String userAvatar;
    private String userProfile;
    private String userRole;
}
