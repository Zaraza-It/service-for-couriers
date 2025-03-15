package org.example.gateway.config;

import lombok.RequiredArgsConstructor;
import org.example.gateway.filters.AuthGlobalFilter;
import org.example.gateway.service.JwtService;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@RequiredArgsConstructor
public class GlobalsBeanFilters {
    private final JwtService jwtService;

    @Bean
    public GlobalFilter AuthFilter(){
        return new AuthGlobalFilter(jwtService);
    }
}
