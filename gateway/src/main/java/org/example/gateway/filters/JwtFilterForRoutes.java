package org.example.gateway.filters;

import lombok.RequiredArgsConstructor;
import org.example.gateway.service.JwtService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtFilterForRoutes extends AbstractGatewayFilterFactory<JwtFilterForRoutes> {
private final JwtService jwtService;

    @Override
    public GatewayFilter apply(JwtFilterForRoutes config) {
        return ((exchange, chain) -> {
            final String accessToken = exchange.getRequest().getHeaders().getFirst("AccessToken");

            exchange.getRequest().getHeaders()
               .set("username", jwtService.getAccessClaims(accessToken).getSubject());

            return chain.filter(exchange);
        });
    }
}
