package org.example.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayRoutes {


@Bean
public RouteLocator setStatusRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("setStatusRoute",r ->
                        r.path("/products/{productId}/status")
                                .filters(f -> f.rewritePath("(?<productId>.*)", "${productId}"))
                                .uri("http://localhost:8080"))
                .build();
    }

@Bean
public RouteLocator getOrderById(RouteLocatorBuilder builder) {
    return builder.routes()
            .route("getOrderByIdRoute",r ->
                    r.path("/products/{productId}")
                            .filters(f -> f.rewritePath("(?<productId>.*)","${productId}"))
                            .uri("http://localhost:8080")).build();
}


@Bean
public RouteLocator getAllOrders(RouteLocatorBuilder builder) {
    return builder.routes()
            .route("getAllOrdersRoute",r ->
                    r.path("/products")
                            .uri("http://localhost:8080")).build();
}

}
