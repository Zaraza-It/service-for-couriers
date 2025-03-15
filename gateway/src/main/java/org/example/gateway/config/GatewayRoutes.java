package org.example.gateway.config;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayRoutes {

    @Value("${location.filter.auth.uri}") private String uri;


@Bean
public RouteLocator setStatusRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("setStatusRoute",r ->
                        r.path("/orders/{orderId}")
                                .filters(f -> f.rewritePath("(?<orderId>.*)", "${orderId}"))
                                .uri("http://localhost:8080"))
                .build();
    }

@Bean
public RouteLocator getOrderById(RouteLocatorBuilder builder) {
    return builder.routes()
            .route("getOrderByIdRoute",r ->
                    r.path("/orders/{orderId}")
                            .filters(f -> f.rewritePath("(?<orderId>.*)","${orderId}"))
                            .uri("http://localhost:8080")).build();
}


@Bean
public RouteLocator getAllOrders(RouteLocatorBuilder builder) {
    return builder.routes()
            .route("getAllOrdersRoute",r ->
                    r.path("/orders")
                            .uri("http://localhost:8080")).build();
}


@Bean
    public RouteLocator create(RouteLocatorBuilder builder) {
    return builder.routes()
            .route("createOrder",r ->
                    r.path("/orders")
                            .uri("http://localhost:8080")).build();
}


@Bean
    public RouteLocator deleteOrder(RouteLocatorBuilder builder) {
    return builder.routes()
            .route("deleteOrder",r ->
            r.path("orders/{orderId}")
                    .filters(f -> f.rewritePath("(?<orderId>.*)","${orderId}"))
                    .uri("http://localhost:8080")).build();
}

    @Bean
    public RouteLocator updateOrder(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("updateOrder",r ->
                        r.path("orders/{orderId}")
                                .filters(f -> f.rewritePath("(?<orderId>.*)","${orderId}"))
                                .uri("http://localhost:8080")).build();
    }

    @Bean
    public RouteLocator login(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("login",r ->
                        r.path("/auth/login")
                                .uri(uri)).build();
    }


}

