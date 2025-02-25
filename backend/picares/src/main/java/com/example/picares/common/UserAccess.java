package com.example.picares.common;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAccess {
    private static final Map<Long, List<HttpSession>> loginSession = new HashMap<>();

    private static final int loginNum=2;

    public static void putLoginSession(Long id,HttpSession session){
        List<HttpSession> sessionList = loginSession.computeIfAbsent(id, k -> new ArrayList<>());

        if (sessionList.size()>=loginNum){
            sessionList.removeFirst();
        }

        sessionList.add(session);
    }

    public static void deleteLoginSession(Long id){
        loginSession.remove(id);
    }

    public static void removeLoginSession(Long id,HttpSession session){
        List<HttpSession> sessionList = loginSession.get(id);

        sessionList.remove(session);

        if (sessionList.isEmpty()){
            loginSession.remove(id);
        }
    }
}
