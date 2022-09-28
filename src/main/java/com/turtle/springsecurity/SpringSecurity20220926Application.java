package com.turtle.springsecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
// mubatis的包扫描
@MapperScan("com.turtle.springsecurity.mapper")
// 开启对应注解
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SpringSecurity20220926Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurity20220926Application.class, args);
    }

}
