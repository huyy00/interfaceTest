package com.example.interfacetest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = "com.example.interfacetest.model")
public class InterfacetestApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterfacetestApplication.class, args);
    }

}