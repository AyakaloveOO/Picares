package com.example.picares.model.entity;

import com.example.picares.common.BusinessException;
import com.example.picares.common.ErrorCode;
import lombok.Data;

@Data
public class Page {
    private int current;
    private int pageSize;
    private String sortField;
    private String sortOrder;
}
