package com.example.picares.service;

import com.example.picares.model.dto.picture.PictureUploadDTO;
import com.example.picares.model.dto.user.UserUpdateDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface PictureService {

    void updateUser(MultipartFile file, PictureUploadDTO pictureUploadDTO, HttpServletRequest request);
}
