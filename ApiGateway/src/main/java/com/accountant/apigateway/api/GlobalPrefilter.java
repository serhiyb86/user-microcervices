package com.accountant.apigateway.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class GlobalPrefilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(GlobalPrefilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("-------------- Pre first filter is executed!!");
        String path = exchange.getRequest().getPath().toString();
        logger.info("Request path is {}", path);
        HttpHeaders headers = exchange.getRequest().getHeaders();
        Set<String> headersString = headers.keySet();
        headersString.forEach(h -> {
            String headerValue = headers.getFirst(h);
            logger.info(h + " " + headerValue);
        });

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
