package com.example.picares.model.entity.picture;

import com.example.picares.model.dto.PageDTO;
import com.example.picares.model.entity.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PictureQuery extends Page {
    private String name;
    private String introduction;
    private String category;
    private String tags;
    private String picFormat;
    private String userAccount;
}
