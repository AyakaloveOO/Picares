package com.example.picares.model.dto.user;

import com.example.picares.common.ErrorCode;
import com.example.picares.common.ThrowUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserRegisterDTO extends UserLoginDTO{
    private String checkPassword;

    public void setCheckPassword(String checkPassword) {
        ThrowUtils.throwIf(checkPassword==null,ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(checkPassword.length()<6, ErrorCode.PARAMS_ERROR,"密码过短");
        ThrowUtils.throwIf(checkPassword.length()>16,ErrorCode.PARAMS_ERROR,"密码过长");
        this.checkPassword = checkPassword;
    }

    @Override
    public String getUserPassword() {
        String userPassword=super.getUserPassword();
        ThrowUtils.throwIf(!userPassword.equals(checkPassword),ErrorCode.PARAMS_ERROR,"密码和确认密码不一致");
        return userPassword;
    }
}
