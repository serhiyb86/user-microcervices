package com.accountant.accesappapiusers.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 400 -> new BadRequestException();
            case 404 -> new ResponseStatusException(HttpStatus.MULTI_STATUS);
            case 500 -> new ResponseStatusException(HttpStatus.MULTI_STATUS);
            default -> new Exception(response.reason());
        };
    }
}
