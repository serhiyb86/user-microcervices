package com.accountant.accesappapiusers;

import feign.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients

public class AccesAppApiUsersApplication {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AccesAppApiUsersApplication.class);

    @Value("${myapplication.env}")
    private String configProfile;

    public static void main(String[] args) {
        SpringApplication.run(AccesAppApiUsersApplication.class, args);
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Profile("!production")
    Logger.Level feignLoggerLover() {
        return Logger.Level.FULL;
    }

    @Bean
    @Profile("production")
    Logger.Level feignLoggerProdLover() {
        return Logger.Level.NONE;
    }

    @Bean
    @Profile("production")
    public String createProductionBean() {
        System.out.println("Production bean has been created. - " + configProfile);
        return "Production bean";
    }

    @Bean
    @Profile("!production")
    public String createNotProductionBean() {
        System.out.println("Not production bean has been created. - " + configProfile);
        return "Not production bean";
    }

    @Bean
    @Profile("default")
    public String createDefaultBean() {
        System.out.println("Default bean has been created. - " + configProfile);
        return "Default bean";
    }


}
