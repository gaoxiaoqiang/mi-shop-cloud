package com.tst.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.tst.mall.service")
public class MiShopAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiShopAuthApplication.class, args);

    }

}
