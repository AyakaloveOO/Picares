package com.example.picares.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserPageVO {
    List<UserVO> records;
    int total;
}
