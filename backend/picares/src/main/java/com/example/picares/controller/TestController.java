package com.example.picares.controller;

import com.example.picares.common.BusinessException;
import com.example.picares.common.ErrorCode;
import com.example.picares.test.UserA;
import com.example.picares.test.UserC;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @PostMapping("/test")
    public String test(@RequestBody UserC userC) {
        System.out.println(userC.getName());
        return userC.getName();
    }
}
