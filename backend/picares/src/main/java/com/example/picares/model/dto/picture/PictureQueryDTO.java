package com.example.picares.model.dto.picture;

import com.example.picares.model.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PictureQueryDTO extends PageDTO {
    private String name;
    private String introduction;
    private String category;
    private String tags;
    private String picFormat;
    private String userAccount;
}
