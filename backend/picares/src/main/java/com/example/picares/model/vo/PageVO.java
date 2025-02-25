package com.example.picares.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVO<T> {
    List<T> records;
    int total;
}
