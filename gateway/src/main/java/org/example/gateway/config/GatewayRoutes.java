package org.example.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutes {


@Bean
public RouteLocator setStatusRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("setStatusRoute",r ->
                        r.path("/products/{productId}/status")
                                .filters(f -> f.modifyRequestBody())
                                .uri("lb://http//localhost:8080"))
                .build();
    }




}
