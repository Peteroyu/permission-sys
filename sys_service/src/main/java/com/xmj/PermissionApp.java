package com.xmj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xmj.mapper")
public class PermissionApp {
    public static void main(String[] args) {
        SpringApplication.run(PermissionApp.class, args);
    }
}
