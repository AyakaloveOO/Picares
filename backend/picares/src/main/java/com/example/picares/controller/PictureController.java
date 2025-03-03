package com.example.picares.controller;

import com.example.picares.annotation.AuthCheck;
import com.example.picares.common.BaseResponse;
import com.example.picares.common.ErrorCode;
import com.example.picares.common.ResultUtils;
import com.example.picares.common.ThrowUtils;
import com.example.picares.model.dto.DeleteDTO;
import com.example.picares.model.dto.picture.PictureQueryDTO;
import com.example.picares.model.dto.picture.PictureUploadDTO;
import com.example.picares.model.enums.UserEnums;
import com.example.picares.model.vo.PageVO;
import com.example.picares.model.vo.PictureAdminVO;
import com.example.picares.service.PictureService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

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

        pictureService.uploadPicture(multipartFile, pictureUploadDTO, request);
        return ResultUtils.success(true);
    }

    @GetMapping("/images/{id}/{path}")
    public void getImage(@PathVariable Long id, @PathVariable String path, HttpServletResponse response) {
        String imagePath = "./images/" + id + File.separator + path;
        getPicture(imagePath, response);
    }

    @GetMapping("/avatar/{id}/{path}")
    public void getAvatar(@PathVariable Long id, @PathVariable String path, HttpServletResponse response) {
        String imagePath = "./avatar/" + id + File.separator + path;
        getPicture(imagePath, response);
    }

    private void getPicture(String imagePath, HttpServletResponse response) {
        try {
            File imageFile = new File(imagePath);
            Files.copy(imageFile.toPath(), response.getOutputStream());
        } catch (Exception e) {
            log.error("图片获取失败", e);
        }
    }

    @PostMapping("/get")
    @AuthCheck(mustRole = UserEnums.ADMIN)
    public BaseResponse<PageVO<PictureAdminVO>> getPictureByPage(@RequestBody PictureQueryDTO pictureQueryDTO) {
        ThrowUtils.throwIf(pictureQueryDTO == null, ErrorCode.PARAMS_ERROR);

        PageVO<PictureAdminVO> pictureByPage = pictureService.getPictureByPage(pictureQueryDTO);
        return ResultUtils.success(pictureByPage);
    }

    @PostMapping("/delete")
    @AuthCheck(mustRole = UserEnums.ADMIN)
    public BaseResponse<Boolean> deletePicture(@RequestBody DeleteDTO deleteDTO) {
        ThrowUtils.throwIf(deleteDTO == null, ErrorCode.PARAMS_ERROR);
        pictureService.deletePicture(deleteDTO);

        return ResultUtils.success(true);
    }
}
