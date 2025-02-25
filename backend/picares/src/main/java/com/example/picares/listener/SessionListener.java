package com.example.picares.listener;

import com.example.picares.common.UserAccess;
import com.example.picares.constant.UserConstant;
import com.example.picares.model.vo.LoginUserVO;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();

        LoginUserVO loginUser = (LoginUserVO) session.getAttribute(UserConstant.USER_LOGIN);
        System.out.println("sesssion destroyed，"+loginUser.toString());
        UserAccess.removeLoginSession(loginUser.getId(),session);
    }
}
