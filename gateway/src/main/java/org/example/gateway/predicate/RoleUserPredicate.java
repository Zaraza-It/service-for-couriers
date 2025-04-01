package org.example.gateway.predicate;

import lombok.RequiredArgsConstructor;
import org.example.gateway.service.JwtService;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;
public class RoleUserPredicate extends AbstractRoutePredicateFactory<RoleUserPredicate.Config> {

    private final JwtService jwtService;

    public RoleUserPredicate(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;

    }

    @Override
    public Predicate<ServerWebExchange> apply(RoleUserPredicate.Config config) {
        return (ServerWebExchange t) -> {
            String token = t.getRequest().getHeaders().getFirst("AccessToken");

            boolean isTrue = false;
            if (token == null) {
                if (jwtService.validateAccessToken(token) == true) {
                   if (jwtService.getAccessClaims(token).get("roles", String.class).equals("ROLE_USER")
                   && jwtService.getAccessClaims(token).get("roles", String.class).equals("ROLE_ADMIN"))
                   {
                        return true;
                    }
                }
            }
            return false;
        };
        }

        @Validated
        public static class Config {
            boolean isTrue = true;
        }
    }


