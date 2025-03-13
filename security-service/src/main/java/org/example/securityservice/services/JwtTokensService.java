package org.example.securityservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.securityservice.configs.UserInfoService;
import org.example.securityservice.model.User;
import org.example.securityservice.response.TokenResponse;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class JwtTokensService {
    private final UserService userService;
    @Value("${token.signing.key}")
private String jwtSecret;
private final UserInfoService userInfo;
public static Logger logger = LogManager.getLogger(JwtTokensService.class);



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

    public boolean validateRefreshToken(@NonNull String token) {
        return validateToken(token);
    }


    public boolean validateAccessToken(@NonNull String token) {
        return validateToken(token);
    }



public boolean validateToken(String token) {
    try {
        Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
        logger.info("Token validated");
        return true;
    } catch (ExpiredJwtException e) {
        logger.error(e.getMessage());
        return false;
    } catch (Exception e){
        logger.error(e.getMessage());
    }
    return false;
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

    public String generateRefreshToken(UserDetails userDetails) {
        if (userDetails instanceof User customUD) {
            return Jwts.builder()
                    .setSubject(customUD.getUsername())
                    .setIssuedAt(Date.from(Instant.now()))
                    .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)

                    .compact();

        }
        return null;
    }
private Key getSigningKey(){
    byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
    return Keys.hmacShaKeyFor(keyBytes);
}

public TokenResponse updateAccessTokenByRefreshToken(String refreshToken) {
    if (validateRefreshToken(refreshToken)) {
        final Claims claims = getRefreshClaims(refreshToken);
        final String username = claims.getSubject();
        final User user = userService.getByUsername(username);
        final String accessToken = generateAccessToken(user);
        return new TokenResponse(accessToken, null);
    }
    return new TokenResponse(null, null);
}

public TokenResponse updateRefresh(String refreshToken) {
        if (validateRefreshToken(refreshToken)) {
            final Claims claims = getRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final User user = userService.getByUsername(username);
            final String accessToken = generateAccessToken(user);
            final String newRefreshToken = generateRefreshToken(user);
            return new TokenResponse(accessToken, newRefreshToken);
        }
        return new TokenResponse(null, null);
}


//public Authentication getAuthInfo(){
     //  return () SecurityContextHolder.getContext().getAuthentication();
//}



}
