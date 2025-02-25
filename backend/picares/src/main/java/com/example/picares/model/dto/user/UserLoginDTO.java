package com.example.picares.model.dto.user;

import com.example.picares.common.BusinessException;
import com.example.picares.common.ErrorCode;
import com.example.picares.common.ThrowUtils;
import lombok.Data;

import java.util.Objects;

@Data
public class UserLoginDTO {
    private String userAccount;
    private String userPassword;

    public void setUserAccount(String userAccount) {

        ThrowUtils.throwIf(userAccount==null,ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(userAccount.length()<4, ErrorCode.PARAMS_ERROR,"账号过短");
        ThrowUtils.throwIf(userAccount.length()>16,ErrorCode.PARAMS_ERROR,"账号过长");
        this.userAccount = userAccount;
    }

    public void setUserPassword(String userPassword) {
        ThrowUtils.throwIf(userPassword==null,ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(userPassword.length()<6, ErrorCode.PARAMS_ERROR,"密码过短");
        ThrowUtils.throwIf(userPassword.length()>16,ErrorCode.PARAMS_ERROR,"密码过长");
        this.userPassword = userPassword;
    }
}
