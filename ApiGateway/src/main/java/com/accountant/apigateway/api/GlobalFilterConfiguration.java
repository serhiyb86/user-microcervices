package com.accountant.apigateway.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(GlobalFilterConfiguration.class);

    @Order(1)
    @Bean
    public GlobalFilter secondPreFilter() {
        return ((exchange, chain) -> {
            logger.info("Custom second global Pre-filter is executed ...");
            return chain.filter(exchange).then(Mono.fromRunnable(() ->
                logger.info("Custom forth global Post-filter is executed ..."))
            );
        });
    }

    @Order(2)
    @Bean
    public GlobalFilter thirdPreFilter() {
        return ((exchange, chain) -> {
            logger.info("Custom third global Pre-filter is executed ...");
            return chain.filter(exchange).then(Mono.fromRunnable(() ->
                logger.info("Custom third global Post-filter is executed ..."))
            );
        });
    }

    @Order(3)
    @Bean
    public GlobalFilter forthPreFilter() {
        return ((exchange, chain) -> {
            logger.info("Custom forth global Pre-filter is executed ...");
            return chain.filter(exchange).then(Mono.fromRunnable(() ->
                logger.info("Custom second global Post-filter is executed ..."))
            );
        });
    }

}
