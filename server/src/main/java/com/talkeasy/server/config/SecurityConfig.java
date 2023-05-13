package com.talkeasy.server.config;

//import com.talkeasy.server.authentication.JwtAuthenticationFilter;

import com.talkeasy.server.authentication.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

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
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-ui.html",
                        "/sign-api/exception/**",
                        "/api/chats/**",
                        "/api/fcm/**",
                        "/api/test/**",
                        "/api/aac/**",
                        "/api/members/**",
                        "/api/follows/**",
                        "/api/alarm/**",
                        "/ws-stomp/**",
                        "/room.html",
                        "/api/alarm/*"
                ).permitAll()
                .anyRequest().authenticated();
        http.csrf().disable();

//        http.oauth2Login()
//                .authorizationEndpoint()
//                .baseUri("/api/oauth2/authorization")
//                .and()
//                .userInfoEndpoint() // 필수
//                .userService(principalOAuth2UserService)
//                .and()
//                .successHandler(oAuth2AuthenticationSucessHandler);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();


    }


}
