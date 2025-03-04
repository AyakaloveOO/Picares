package com.example.picares.model.entity.user;

import com.example.picares.common.ErrorCode;
import com.example.picares.common.ThrowUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class UserRegister {
    private String userAccount;
    private String userPassword;
    private String userName;
}
