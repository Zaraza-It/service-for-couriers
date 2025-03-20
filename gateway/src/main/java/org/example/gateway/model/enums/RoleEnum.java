package org.example.gateway.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_COURIER;

    @Override
    public String getAuthority() {
        return name();
    }
}
