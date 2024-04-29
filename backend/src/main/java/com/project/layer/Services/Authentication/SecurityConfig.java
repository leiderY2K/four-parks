package com.project.layer.Services.Authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.layer.Persistence.Entity.Permission;
import com.project.layer.Services.JWT.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
            .csrf(csrf ->
                csrf
                    .disable()
                )
            .authorizeHttpRequests(authRequest -> {
                authRequest
                    //Peticiones publicas
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/error").permitAll()

                    //Peticiones privadas
                    .requestMatchers("/client/**").hasAuthority(Permission.READ_ALL_PRODUCTS.name())
                    .requestMatchers("/admin/**").hasAuthority(Permission.READ_ALL_PRODUCTS.name())

                    .anyRequest().authenticated();
                })
            .sessionManagement(sessionManager ->
                    sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
