package com.learn.simplegateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * API网关服务
 *
 * @ EnableOAuth2Sso 启用OAuth2单点登录
 */
@SpringBootApplication(scanBasePackages = {"conf", "authentication.*"})
@EnableEurekaClient
public class SimpleGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleGatewayApplication.class, args);
    }

}
