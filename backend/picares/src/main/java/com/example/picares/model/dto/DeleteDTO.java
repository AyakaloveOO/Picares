package com.example.picares.model.dto;

import com.example.picares.common.ErrorCode;
import com.example.picares.common.ThrowUtils;
import lombok.Data;

@Data
public class DeleteDTO {
    private Long id;

    public Long getId() {
        ThrowUtils.throwIf(id==null,ErrorCode.PARAMS_ERROR);
        return id;
    }
}
