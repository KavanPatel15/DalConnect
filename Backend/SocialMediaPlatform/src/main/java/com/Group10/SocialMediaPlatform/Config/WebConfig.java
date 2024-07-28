package com.Group10.SocialMediaPlatform.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    // Define a bean for CORS configuration
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            // Configure CORS mappings
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow CORS requests to all endpoints
                        .allowedOrigins("*") // Allow all origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Allow specified HTTP methods
                        .allowedHeaders("*"); // Allow all headers
            }
        };
    }
}
