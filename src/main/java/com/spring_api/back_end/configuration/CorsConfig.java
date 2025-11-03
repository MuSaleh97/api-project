package com.spring_api.back_end.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow all origins (use specific domains in production)
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));

        // Allow all headers
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Allow all HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));

        // Allow credentials
        configuration.setAllowCredentials(true);

        // Set max age for preflight requests
        configuration.setMaxAge(3600L);

        // Expose common headers
        configuration.setExposedHeaders(Arrays.asList(
            "Authorization", "Content-Type", "Accept", "X-Requested-With",
            "Cache-Control", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers"
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(source);
    }
}
