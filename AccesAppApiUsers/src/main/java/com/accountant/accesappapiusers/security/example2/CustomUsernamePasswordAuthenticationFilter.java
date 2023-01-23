package com.accountant.accesappapiusers.security.example2;

import com.accountant.accesappapiusers.model.dto.UserDto;
import com.accountant.accesappapiusers.model.requests.LoginRequestModel;
import com.accountant.accesappapiusers.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

//We can use this filter instead of AuthentificationService
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final UserService userService;
    private final String tokenExpTime;
    private final String tokenSecret;


    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                      UserService userService,
                                                      String tokenExpTime,
                                                      String tokenSecret,
                                                      String loginPath) {
        super(authenticationManager);
        this.userService = userService;
        this.tokenExpTime = tokenExpTime;
        this.tokenSecret = tokenSecret;
        this.setFilterProcessesUrl(loginPath);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestModel creds = new ObjectMapper()
                .readValue(request.getInputStream(), LoginRequestModel.class);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                creds.getEmail(),
                creds.getPassword(),
                new ArrayList<>()));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        SecretKey key = new SecretKeySpec(tokenSecret.getBytes(StandardCharsets.UTF_8), 0, tokenSecret.getBytes(StandardCharsets.UTF_8).length,
            "HmacSHA512");

        String username = ((User) authResult.getPrincipal()).getUsername();

        UserDto userDto = userService.getUserDetailsByEmail(username);
        // String token = JwtUtils.generateToken(userDto, tokenSecret, tokenExpTime);
        // response.addHeader("token", token);
        response.addHeader("userId", userDto.getUserId());
    }


}
