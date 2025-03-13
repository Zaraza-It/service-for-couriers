package org.example.authservice.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_USER,
    ;

    @Override
    public String getAuthority() {
        return name();
    }
}
