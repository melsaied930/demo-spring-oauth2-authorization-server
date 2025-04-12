package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(1)
    SecurityFilterChain adminApiSecurity(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/admin/**")
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().hasRole("ADMIN"))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain userApiSecurity(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/user/**")
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().hasAnyRole("USER", "ADMIN"))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    @Order(3)
    SecurityFilterChain publicApiSecurity(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/public/**")
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().permitAll())
                .build();
    }

    @Bean
    @Order(4)
    SecurityFilterChain h2ConsoleSecurity(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().permitAll())
                .csrf(csrf ->
                        csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .build();
    }

}
