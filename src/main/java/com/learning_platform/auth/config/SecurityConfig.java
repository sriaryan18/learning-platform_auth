package com.learning_platform.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http
            .csrf(customizer -> customizer.disable())
            .authorizeHttpRequests((authz) -> authz
            .requestMatchers("/auth/api/v1/login","/auth/api/v1/signup","/auth/api/v1/verifyToken").permitAll()
            .anyRequest()
            .authenticated()
            ).build();
    }
    
}
