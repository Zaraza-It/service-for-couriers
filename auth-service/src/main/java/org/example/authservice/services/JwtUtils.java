package org.example.authservice.services;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.authservice.model.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {

public static  JwtAuthentication generate(Claims claims) {
    final JwtAuthentication jwtAuthentication = new JwtAuthentication();
    jwtAuthentication.setAuthenticated(true);
    jwtAuthentication.setRoles(getRoles(claims));
    return jwtAuthentication;
}




private static Set<Role> getRoles(Claims claims) {
    final List<String> roles =claims.get("roles", List.class);
    return roles.stream()
            .map(Role::valueOf)
            .collect(Collectors.toSet());
}


}
