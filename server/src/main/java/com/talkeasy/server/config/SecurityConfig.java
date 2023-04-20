package com.talkeasy.server.config;

//import com.talkeasy.server.authentication.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                .antMatchers("/favicon.ico").permitAll()
                .antMatchers(
                        "/",
                        "/api/oauth/**",
                        // Swagger 관련 URL
                        "/v2/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger/**",
                        "/sign-api/exception/**"
                ).permitAll()
                .anyRequest().authenticated();
        http.csrf().disable();
        return http.build();


    }


}
