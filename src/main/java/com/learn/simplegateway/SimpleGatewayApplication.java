package com.learn.simplegateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import authentication.filter.RateLimitByIpGatewayFilter;

/**
 * API网关服务
 *
 * @ EnableOAuth2Sso 启用OAuth2单点登录
 * @author Administrator
 */
@SpringBootApplication(scanBasePackages = {"conf", "authentication.*"})
@EnableEurekaClient
@EnableConfigurationProperties(value = {RateLimitByIpGatewayFilter.class})
public class SimpleGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleGatewayApplication.class, args);
    }

}
