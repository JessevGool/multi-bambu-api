package com.jessevangool.multi_bambu_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/api/**")
                        .allowedOriginPatterns(
                                "http://192.168.*.*",
                                "http://10.*.*.*",
                                "http://172.16.*.*",
                                "http://localhost:*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}