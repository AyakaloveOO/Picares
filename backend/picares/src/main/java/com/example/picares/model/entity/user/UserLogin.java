package com.example.picares.model.entity.user;

import com.example.picares.common.ErrorCode;
import com.example.picares.common.ThrowUtils;
import lombok.Data;

@Data
public class UserLogin {
    private String userAccount;
    private String userPassword;
}
