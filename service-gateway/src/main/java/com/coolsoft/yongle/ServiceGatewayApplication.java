package com.coolsoft.yongle;

import com.coolsoft.yongle.gateway.filter.factory.AuthTokenGatewayFilterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceGatewayApplication.class, args);
    }

    @Bean
    AuthTokenGatewayFilterFactory authTokenGatewayFilterFactory(){
        return new AuthTokenGatewayFilterFactory();
    }
}
