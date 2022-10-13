package com.yang.wxlogin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: yscong
 * @create: 2022/7/7 15:29
 */
@ComponentScan({"com.yang"})
@SpringBootApplication
@MapperScan("com.yang.wxlogin.mapper")
public class WxloginApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxloginApplication.class, args);
    }
}