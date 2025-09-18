package com.ridehailing.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF
            .csrf(csrf -> csrf.disable())
            // 2. Configure authorization rules
            .authorizeHttpRequests(auth -> auth
                // Allow anyone to access the /login endpoint
                .requestMatchers("/api/v1/auth/**").permitAll()
                // All other requests must be authenticated (we'll use this later)
                .anyRequest().authenticated()
            );

        return http.build();
    }
}