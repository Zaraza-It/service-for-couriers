package org.example.securityservice.services;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.securityservice.model.enums.RoleEnum;

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




private static Set<RoleEnum> getRoles(Claims claims) {
    final List<String> roles = claims.get("roles", List.class);
    return roles.stream()
            .map(RoleEnum::valueOf)
            .collect(Collectors.toSet());
}


}
