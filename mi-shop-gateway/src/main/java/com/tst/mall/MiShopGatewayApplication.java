package com.tst.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MiShopGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiShopGatewayApplication.class, args);
	}

}
