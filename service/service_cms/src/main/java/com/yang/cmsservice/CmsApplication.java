package com.yang.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: yscong
 * @create: 2022/7/5 22:51
 */
@SpringBootApplication
@ComponentScan({"com.yang"}) //指定扫描位置
@MapperScan("com.yang.cmsservice.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
