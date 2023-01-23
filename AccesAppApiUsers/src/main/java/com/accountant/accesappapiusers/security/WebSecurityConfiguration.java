package com.accountant.accesappapiusers.security;

import com.accountant.accesappapiusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true)
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    // private final CustomUsernamePasswordAuthenticationFilter authFilter;
    private final JwtAuthenticationFilter jwtAuthFilter;

    private UserService userService;
    @Value("${token.expiration_time}")
    private String tokenExpTime;
    @Value("${token.secret}")
    private String tokenSecret;
    @Value("${login.url.path}")
    private String loginPath;

    @Bean
    static GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService)
        throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userService)
            .passwordEncoder(bCryptPasswordEncoder)
            .and()
            .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager manager) throws Exception {
//        CustomUsernamePasswordAuthenticationFilter af = new CustomUsernamePasswordAuthenticationFilter(manager,
//            userService, tokenExpTime, tokenSecret, loginPath);


        http.csrf().disable();
        http.authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/users")
            .permitAll()
            .and()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, loginPath)
            .permitAll()
            .anyRequest()//all another should be authenticated
            .authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
