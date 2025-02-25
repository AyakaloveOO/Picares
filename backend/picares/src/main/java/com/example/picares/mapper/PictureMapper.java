package com.example.picares.mapper;

import com.example.picares.model.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

public interface PictureMapper {

    void insertPicture(Picture picture);
}
