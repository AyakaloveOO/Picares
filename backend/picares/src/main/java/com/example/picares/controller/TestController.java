package com.example.picares.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test/{id}")
    public String test(@RequestParam("id") Long id) {

        return "aaaaa";
    }
}
