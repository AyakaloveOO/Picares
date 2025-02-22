package com.example.picares.aop;

import com.example.picares.annotation.AuthCheck;
import com.example.picares.common.BusinessException;
import com.example.picares.common.ErrorCode;
import com.example.picares.model.enums.UserEnums;
import com.example.picares.model.vo.LoginUserVO;
import com.example.picares.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthInterceptor {
    @Resource
    private UserService userService;

    @Before("@annotation(authCheck)")
    public void doInterceptor(AuthCheck authCheck) {
        UserEnums mustRole = authCheck.mustRole();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        LoginUserVO loginUser = userService.getLoginUser(request);

        //判断用户权限是否合法
        UserEnums userRole = UserEnums.getEnumByName(loginUser.getUserRole());
        if (userRole == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        //校验权限
        if (userRole.getValue()< mustRole.getValue()) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
    }
}
