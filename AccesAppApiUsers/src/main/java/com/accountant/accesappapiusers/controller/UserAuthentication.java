package com.accountant.accesappapiusers.controller;

import com.accountant.accesappapiusers.model.requests.LoginRequestModel;
import com.accountant.accesappapiusers.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login-ws")
@RequiredArgsConstructor
public class UserAuthentication {

    private final AuthenticationService authService;

    @PostMapping
    public ResponseEntity login(@RequestBody LoginRequestModel loginRequest) {
        return authService.authenticate(loginRequest);
    }
}
