package org.example.gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.apache.el.parser.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.gateway.model.User;
import org.example.gateway.response.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtService {
    Logger logger = LogManager.getLogger(JwtService.class);
    @Value("${token.signing.key}") String signingKey;
private final UserService userService;
public boolean validateToken(String token) {
    try {
        Jwts.parser()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token);
        logger.info("Token successfully validated");
        return true;
    } catch (ExpiredJwtException e){
        logger.error("Token is expired");
    } catch (Exception e) {
        logger.error(e.getMessage());
        return false;
    }
    return false;
}
public boolean validateAccessToken(@NonNull String token) {
    return validateToken(token);
}

public boolean validateRefreshToken(@NonNull String token) {
    return validateToken(token);
}

    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Claims getRefreshClaims(@NonNull String token) {
        return extractAllClaims(token);
    }

    public Claims getAccessClaims(@NonNull String token) {
        return extractAllClaims(token);
    }

    public TokenResponse updateAccessTokenByRefreshToken(String refreshToken) {
        if (validateRefreshToken(refreshToken)) {
            final Claims claims = getRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final User user = userService.getByUsername(username);
            if (user != null) {
                final String accessToken = generateAccessToken(user);
                return new TokenResponse(accessToken, null);
            }
        }
        return new TokenResponse(null, null);
    }
    public String generateAccessToken(UserDetails userDetails) {
        if (userDetails instanceof User customUD) {
            return Jwts.builder()
                    .setSubject(customUD.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .claim("username", customUD.getUsername())
                    .claim("roles", customUD.getRoles())
                    .compact();
        }
        return null;
    }
}
