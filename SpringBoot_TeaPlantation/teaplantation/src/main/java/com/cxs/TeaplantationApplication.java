package com.cxs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
@MapperScan(value = "com.cxs.mapper")
public class TeaplantationApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeaplantationApplication.class, args);
    }

}
