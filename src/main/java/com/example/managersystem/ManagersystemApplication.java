package com.example.managersystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan(value = "com.example.managersystem.mapper")
public class ManagersystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagersystemApplication.class, args);
    }

}
