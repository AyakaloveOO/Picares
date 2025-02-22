package com.example.picares.service;

import com.example.picares.model.dto.user.UserLoginDTO;
import com.example.picares.model.dto.user.UserQueryDTO;
import com.example.picares.model.dto.user.UserRegisterDTO;
import com.example.picares.model.dto.user.UserUpdateDTO;
import com.example.picares.model.vo.LoginUserVO;
import com.example.picares.model.vo.UserPageVO;
import com.example.picares.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {
    void userRegister(UserRegisterDTO userRegisterDTO);

    LoginUserVO userLogin(UserLoginDTO userLoginDTO, HttpServletRequest request);

    LoginUserVO getLoginUser(HttpServletRequest request);

    void userLogout(HttpServletRequest request);

    void updateUser(UserUpdateDTO userUpdateDTO);

    void deleteUser(Long id);

    UserPageVO getUserByPage(UserQueryDTO userQueryDTO);
}
