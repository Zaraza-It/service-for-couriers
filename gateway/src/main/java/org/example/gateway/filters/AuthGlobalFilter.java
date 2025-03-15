package org.example.gateway.filters;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.gateway.response.TokenResponse;
import org.example.gateway.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    public final JwtService jwtService;
    public AuthGlobalFilter(final JwtService jwtService) {
        this.jwtService = jwtService;
    }
    public static final String BEARER_PREFIX = "Bearer ";
    @Value("${location.filter.auth.uri}") String url;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (exchange.getRequest().getHeaders() != null) {
            if (exchange.getRequest().getHeaders().get("RefreshToken") != null) {
        String refreshToken = exchange.getRequest().getHeaders().getFirst("RefreshToken");
            if (jwtService.validateRefreshToken(refreshToken) == true) {
                String accessToken = exchange.getRequest().getHeaders().getFirst("AccessToken");
                if (accessToken != null) {
                    if (jwtService.validateAccessToken(accessToken) == true) {
                        exchange.getRequest().getPath();
                    }
                    else if (jwtService.validateRefreshToken(refreshToken) == true) {
                        TokenResponse responseToken = jwtService.updateAccessTokenByRefreshToken(refreshToken);
                        exchange.getResponse().getHeaders().set("RefreshToken", accessToken);
                        exchange.getResponse().getHeaders().set("AccessToken", responseToken.getAccessToken());
                        return chain.filter(exchange);
                    } else if (exchange.getRequest().getPath().equals(url + "/auth/regitration")){
                        exchange.getRequest().getBody();
                        exchange.getResponse().getHeaders();
                        exchange.getRequest().getPath();
                    }
                    else exchange.getResponse().getHeaders().setLocation(URI.create(url + "/auth/login"));
                    System.out.println("Перешёл в login");
                    exchange.getResponse().getHeaders();
                    exchange.getRequest().getBody();
                    exchange.getRequest().getPath();
                    exchange.getResponse().setStatusCode(HttpStatus.OK);
                }
            }


            }
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
