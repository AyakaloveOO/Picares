package com.example.picares.mapper;

import com.example.picares.model.entity.picture.PictureQuery;
import com.example.picares.model.entity.picture.PictureUpload;
import com.example.picares.model.vo.PictureAdminVO;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PictureMapper {

    void insertPicture(PictureUpload pictureUpload);

    List<PictureAdminVO> getPictureByPage(PictureQuery pictureQuery);

    int countPictureByPage(PictureQuery pictureQuery);

    @Update("update picture set isDelete=1 where id=#{id}")
    void deletePicture(Long id);
}
