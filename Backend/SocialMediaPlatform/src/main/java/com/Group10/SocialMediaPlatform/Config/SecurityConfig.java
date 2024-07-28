package com.Group10.SocialMediaPlatform.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Define the security filter chain bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CORS to ensure proper configuration elsewhere
                .cors(AbstractHttpConfigurer::disable)
                // Disable CSRF protection
                .csrf(AbstractHttpConfigurer::disable)
                // Configure authorization for HTTP requests
                .authorizeHttpRequests(authorize -> authorize
                        // Permit all requests without authentication
                        .anyRequest().permitAll()
                );

        // Build and return the SecurityFilterChain
        return http.build();
    }

    // Define the password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use BCryptPasswordEncoder to encode passwords
        return new BCryptPasswordEncoder();
    }
}
