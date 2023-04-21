package com.talkeasy.server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("*")
                .allowedOrigins("http://192.168.100.57:8081", "http://192.168.100.58:8081", "http://localhost:8081", "https://j8d207.p.ssafy.io", "http://j8d207.p.ssafy.io:8081")
                .allowCredentials(true)
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(), HttpMethod.HEAD.name(), HttpMethod.OPTIONS.name(),
                        HttpMethod.PATCH.name())
                .maxAge(1000);
    }

}