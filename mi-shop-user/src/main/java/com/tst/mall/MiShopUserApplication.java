package com.tst.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.tst.mall.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class MiShopUserApplication {

    public static void main(String[] args) {

        SpringApplication.run(MiShopUserApplication.class, args);
        System.out.println("服务名: " + System.getProperty("spring.application.name"));

    }

}
