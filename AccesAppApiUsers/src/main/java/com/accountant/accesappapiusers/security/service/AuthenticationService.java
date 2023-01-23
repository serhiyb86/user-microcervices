package com.accountant.accesappapiusers.security.service;

import com.accountant.accesappapiusers.model.requests.LoginRequestModel;
import com.accountant.accesappapiusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService service;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity authenticate(LoginRequestModel request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        var user = service.getUserDetailsByEmail(request.getEmail());

        var jwtToken = jwtService.generateToken(user);
        return ResponseEntity
            .status(200)
            .header("token", jwtToken)
            .header("userId", user.getUserId())
            .build();
    }
}
