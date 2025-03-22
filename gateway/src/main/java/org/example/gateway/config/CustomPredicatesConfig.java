package org.example.gateway.config;

import org.example.gateway.predicate.RoleAdminPredicate;
import org.example.gateway.predicate.RoleUserPredicate;
import org.example.gateway.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomPredicatesConfig {

    @Bean
    public RoleUserPredicate rolePredicate(
            JwtService jwtService
    ) {
        return new RoleUserPredicate(jwtService);
    }

    @Bean
    public RoleAdminPredicate roleAdminPredicate(
            JwtService jwtService
    ) {
        return new RoleAdminPredicate(jwtService);
    }
}
