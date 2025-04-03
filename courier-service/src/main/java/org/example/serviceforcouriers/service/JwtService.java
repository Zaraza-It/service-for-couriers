package org.example.serviceforcouriers.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {
    Logger logger = LogManager.getLogger(JwtService.class);

    @Value("${token.signing.key}")
    String signingKey;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Key secretCode() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(signingKey));
    }

    public Claims extractAllClaims(@NonNull String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretCode())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return extractAllClaims(token);
    }
}