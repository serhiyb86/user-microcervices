package com.accountant.accountantappapiconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class AccountantAppApiConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountantAppApiConfigServerApplication.class, args);
    }

}
