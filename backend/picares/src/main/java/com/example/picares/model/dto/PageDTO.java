package com.example.picares.model.dto;

import com.example.picares.common.BusinessException;
import com.example.picares.common.ErrorCode;
import lombok.Data;

@Data
public class PageDTO {
    private int current=1;
    private int pageSize=10;
    private String sortField;
    private String sortOrder="DESC";

    public void setSortOrder(String sortOrder) {
        if ("ascend".equals(sortOrder)) {
            this.sortOrder = "ASC";
        }else if ("descend".equals(sortOrder)) {
            this.sortOrder = "DESC";
        }else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }
}
