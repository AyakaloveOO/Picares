package com.example.picares.controller;

import com.example.picares.annotation.AuthCheck;
import com.example.picares.common.BaseResponse;
import com.example.picares.common.ErrorCode;
import com.example.picares.common.ResultUtils;
import com.example.picares.common.ThrowUtils;
import com.example.picares.model.dto.DeleteDTO;
import com.example.picares.model.dto.user.UserLoginDTO;
import com.example.picares.model.dto.user.UserQueryDTO;
import com.example.picares.model.dto.user.UserRegisterDTO;
import com.example.picares.model.dto.user.UserUpdateDTO;
import com.example.picares.model.enums.UserEnums;
import com.example.picares.model.vo.LoginUserVO;
import com.example.picares.model.vo.PageVO;
import com.example.picares.model.vo.UserVO;
import com.example.picares.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Boolean> userRegister(@RequestBody UserRegisterDTO userRegisterDTO) {
        ThrowUtils.throwIf(userRegisterDTO == null, ErrorCode.PARAMS_ERROR);

        userService.userRegister(userRegisterDTO);
        return ResultUtils.success(true);
    }

    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        ThrowUtils.throwIf(userLoginDTO == null, ErrorCode.PARAMS_ERROR);

        LoginUserVO user = userService.userLogin(userLoginDTO, request);
        return ResultUtils.success(user);
    }

    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        LoginUserVO loginUser = userService.getLoginUser(request);

        return ResultUtils.success(loginUser);
    }

    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        userService.userLogout(request);

        return ResultUtils.success(true);
    }

    @PostMapping("/update")
    @AuthCheck(mustRole = UserEnums.ADMIN)
    public BaseResponse<Boolean> updateUser(@RequestParam(name = "avatar", required = false) MultipartFile multipartFile, @ModelAttribute UserUpdateDTO userUpdateDTO) {
        ThrowUtils.throwIf(userUpdateDTO == null, ErrorCode.PARAMS_ERROR);
        if (multipartFile == null) {
            userService.updateUser(userUpdateDTO);
        } else {
            userService.updateUser(multipartFile, userUpdateDTO);
        }
        return ResultUtils.success(true);
    }

    @PostMapping("/get")
    @AuthCheck(mustRole = UserEnums.ADMIN)
    public BaseResponse<PageVO<UserVO>> getUserByPage(@RequestBody UserQueryDTO userQueryDTO) {
        ThrowUtils.throwIf(userQueryDTO == null, ErrorCode.PARAMS_ERROR);

        PageVO<UserVO> userPage = userService.getUserByPage(userQueryDTO);
        return ResultUtils.success(userPage);
    }

    @PostMapping("/delete")
    @AuthCheck(mustRole = UserEnums.ADMIN)
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteDTO deleteDTO) {
        ThrowUtils.throwIf(deleteDTO == null, ErrorCode.PARAMS_ERROR);

        userService.deleteUser(deleteDTO.getId());
        return ResultUtils.success(true);
    }
}
