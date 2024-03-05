package com.example.ecommerceapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().requestMatchers("/api/**").permitAll()
                .anyRequest().authenticated();
        http.headers().frameOptions().disable();
        return http.build();
    }

    // authenticated().requestMatchers("/api/**")
    // @Bean
    // SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws
    // Exception {
    // http.authorizeHttpRequests((requests) -> {
    // requests.requestMatchers("/api/**",
    // "/h2-console/**").permitAll().anyRequest().authenticated();
    // });
    // http.formLogin(Customizer.withDefaults());
    // http.httpBasic(Customizer.withDefaults());
    // return (SecurityFilterChain) http.build();
    // }

    // @Bean
    // public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    // http
    // .authorizeHttpRequests(requests -> requests
    // .requestMatchers(HttpMethod.GET, "/", "/static/**", "/index.html",
    // "/api/users/me").permitAll()
    // .requestMatchers(HttpMethod.POST, "/api/**").permitAll()
    // .requestMatchers(HttpMethod.GET, "/api/users/login", "/api/users/{username}",
    // "/api/users/logout", "/api/customers", "/api/storages")
    // .authenticated()
    // .requestMatchers(HttpMethod.POST, "/api/customers",
    // "/api/storages").authenticated()
    // .requestMatchers(HttpMethod.PUT, "/api/customers/{id}",
    // "/api/storages/{id}").authenticated()
    // .requestMatchers(HttpMethod.DELETE, "/api/users/{id}", "/api/storages/{id}",
    // "/api/customers/{id}")
    // .authenticated()
    // .anyRequest().denyAll());
    // return http.build();
    // }
}