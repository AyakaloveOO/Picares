package com.example.picares.model.dto.picture;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

@Data
public class PictureUploadDTO {
    private String name;
    private String introduction;
    private String category;
    private String tags;

    public void setName(String name) {
        if (StrUtil.isBlank(name)){
            name="图片"+ RandomUtil.randomNumbers(8);
        }
        this.name = name;
    }
}
