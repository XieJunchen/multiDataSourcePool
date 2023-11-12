package com.wyt.HikariCP.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wyt.HikariCP.demo.mapper")
public class HikariCpDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HikariCpDemoApplication.class, args);
    }

}
