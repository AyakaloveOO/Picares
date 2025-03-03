package com.example.picares.mapper;

import com.example.picares.model.dto.picture.PictureQueryDTO;
import com.example.picares.model.entity.Picture;
import com.example.picares.model.vo.PictureAdminVO;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PictureMapper {

    void insertPicture(Picture picture);

    List<PictureAdminVO> getPictureByPage(PictureQueryDTO pictureQueryDTO);

    int countPictureByPage(PictureQueryDTO pictureQueryDTO);

    @Update("update picture set isDelete=1 where id=#{id}")
    void deletePicture(Long id);
}
