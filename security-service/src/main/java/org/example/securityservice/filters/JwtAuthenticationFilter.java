package org.example.securityservice.filters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.securityservice.response.TokenResponse;
import org.example.securityservice.services.JwtTokensService;
import org.example.securityservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION = "Authorization";
    private static final String NOTOKEN = "NoTokenAuth";
    public static final String BEARER_PREFIX = "Bearer ";
    private final UserService userService;
    private final JwtTokensService jwtTokensService;
    public final static Logger logger = LogManager.getLogger(JwtTokensService.class);

    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @org.springframework.lang.NonNull HttpServletResponse response,
           @org.springframework.lang.NonNull FilterChain filterChain)
            throws ServletException, IOException {
        var token = request.getHeader(AUTHORIZATION);
        if (StringUtils.isEmpty(token) || !token.startsWith(BEARER_PREFIX)) {
            logger.info("Хэдер на регистрацию");
            filterChain.doFilter(request, response);
            return;
        }

        var username = request.getHeader("Username");
        var noToken = request.getHeader(NOTOKEN);

        if (noToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          try {
              UserDetails userDetails = userService.getUserDetailsService().loadUserByUsername(username);
              logger.info(username);
              SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
              UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                      userDetails, null, userDetails.getAuthorities()
              );
              securityContext.setAuthentication(authenticationToken);
              SecurityContextHolder.setContext(securityContext);
              authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          } catch (NullPointerException e){
              logger.info(e.getMessage());
          }
          }
        var jwt = token.substring(BEARER_PREFIX.length());
        var refreshToken = request.getHeader("RefreshToken");


        if (SecurityContextHolder.getContext().getAuthentication() == null && refreshToken != null && jwt != null ) {
            try {
            if (jwtTokensService.validateRefreshToken(refreshToken) == true) {
                logger.info("Валид");
                    if (jwtTokensService.validateAccessToken(jwt) == true) {
                        logger.info("AccessToken валид");
                        var usernameInToken = jwtTokensService.getAccessClaims(jwt.toString()).getSubject();

                        UserDetails userDetails = userService.getUserDetailsService().loadUserByUsername(usernameInToken);

                        SecurityContext context = SecurityContextHolder.createEmptyContext();

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                        context.setAuthentication(authentication);
                        SecurityContextHolder.setContext(context);
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    } else if (jwtTokensService.validateRefreshToken(refreshToken) == true) {
                        logger.info("Перешёл в исключение");
                        TokenResponse tokenResponse = jwtTokensService.updateAccessTokenByRefreshToken(refreshToken);
                        if (jwtTokensService.validateAccessToken(tokenResponse.getAccessToken())) {
                            var usernameInTokenNew = jwtTokensService.getAccessClaims(tokenResponse.getAccessToken()).getSubject();

                            UserDetails userDetails = userService.getUserDetailsService().loadUserByUsername(usernameInTokenNew);

                            SecurityContext context = SecurityContextHolder.createEmptyContext();

                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );
                            context.setAuthentication(authentication);
                            SecurityContextHolder.setContext(context);
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            response.addHeader("AccessToken", tokenResponse.getAccessToken());
                            Cookie cookie = new Cookie("refresh", refreshToken);
                            response.addCookie(cookie);
                         filterChain.doFilter(request, response);
                        }
                    }

            }
            } catch (ExpiredJwtException e) {
                logger.error("Токен не валид");
                response.sendRedirect("http://localhost:7777/auth/update");
                filterChain.doFilter(request, response);
            }
        }


    }
}
