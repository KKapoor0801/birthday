package com.example.birthday.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // disable CSRF for non-browser clients
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/birthday/insertBirthday").authenticated() // secure this
                .anyRequest().permitAll()
                .and()
                .httpBasic(); // enable Basic Auth

        return http.build();
    }
}

