package com.example.picares.service;

import com.example.picares.model.dto.DeleteDTO;
import com.example.picares.model.dto.picture.PictureQueryDTO;
import com.example.picares.model.dto.picture.PictureUploadDTO;
import com.example.picares.model.vo.PageVO;
import com.example.picares.model.vo.PictureAdminVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface PictureService {

    void uploadPicture(MultipartFile file, PictureUploadDTO pictureUploadDTO, HttpServletRequest request);

    PageVO<PictureAdminVO> getPictureByPage(PictureQueryDTO pictureQueryDTO);

    void deletePicture(DeleteDTO deleteDTO);
}
