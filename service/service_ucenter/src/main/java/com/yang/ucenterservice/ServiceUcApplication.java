package com.yang.ucenterservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: yscong
 * @create: 2022/7/7 15:29
 */
@ComponentScan({"com.yang"})
@SpringBootApplication
@MapperScan("com.yang.ucenterservice.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceUcApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUcApplication.class, args);
    }
}