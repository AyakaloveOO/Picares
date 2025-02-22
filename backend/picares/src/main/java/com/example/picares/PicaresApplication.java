package com.example.picares;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@MapperScan("com.example.picares.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
@ServletComponentScan(basePackages="com.example.picares")
@SpringBootApplication
public class PicaresApplication {

    public static void main(String[] args) {
        SpringApplication.run(PicaresApplication.class, args);
    }

}
