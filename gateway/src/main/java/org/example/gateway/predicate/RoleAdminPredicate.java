package org.example.gateway.predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

public class RoleAdminPredicate extends AbstractRoutePredicateFactory<RoleAdminPredicate.Config> {

    public RoleAdminPredicate() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return null;
    }

    @Validated
    public static class Config() {
        boolean isTrue = true;
    }

}

