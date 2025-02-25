package com.example.picares.controller;

import com.example.picares.annotation.AuthCheck;
import com.example.picares.common.BaseResponse;
import com.example.picares.common.ErrorCode;
import com.example.picares.common.ResultUtils;
import com.example.picares.common.ThrowUtils;
import com.example.picares.model.dto.picture.PictureUploadDTO;
import com.example.picares.model.enums.UserEnums;
import com.example.picares.service.PictureService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {
    @Resource
    private PictureService pictureService;

    @PostMapping("/upload")
    @AuthCheck(mustRole = UserEnums.USER)
    public BaseResponse<Boolean> uploadPicture(@RequestParam("image") MultipartFile multipartFile, @ModelAttribute PictureUploadDTO pictureUploadDTO, HttpServletRequest request) {
        ThrowUtils.throwIf(multipartFile == null, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(pictureUploadDTO == null, ErrorCode.PARAMS_ERROR);

        pictureService.updateUser(multipartFile, pictureUploadDTO, request);
        return ResultUtils.success(true);
    }
}
