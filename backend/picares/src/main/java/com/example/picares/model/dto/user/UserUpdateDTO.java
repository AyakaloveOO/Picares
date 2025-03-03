package com.example.picares.model.dto.user;

import com.example.picares.common.ErrorCode;
import com.example.picares.common.ThrowUtils;
import com.example.picares.model.enums.UserEnums;
import lombok.Data;

@Data
public class UserUpdateDTO {
    private Long id;
    private String userAccount;
    private String userName;
    private String userAvatar;
    private String userProfile;
    private String userRole;

    public void setUserName(String userName) {
        ThrowUtils.throwIf(userName.length()<32, ErrorCode.PARAMS_ERROR,"用户名称过长");
        this.userName = userName;
    }
    public void setUserAvatar(String userAvatar) {
        ThrowUtils.throwIf(userAvatar.length()>256,ErrorCode.PARAMS_ERROR);
        this.userAvatar = userAvatar;
    }

    public void setUserProfile(String userProfile) {
        ThrowUtils.throwIf(userProfile.length()>128,ErrorCode.PARAMS_ERROR,"用户简介过长");
        this.userProfile = userProfile;
    }

    public void setUserRole(String userRole) {
        ThrowUtils.throwIf(UserEnums.getEnumByName(userRole)==null,ErrorCode.PARAMS_ERROR);
        this.userRole = userRole;
    }
}
