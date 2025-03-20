package org.example.securityservice.services;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.securityservice.dao.UserRepository;
import org.example.securityservice.model.User;
import org.example.securityservice.model.enums.RoleEnum;
import org.example.securityservice.requests.JwtAuthenticationResponse;
import org.example.securityservice.requests.Register;

import org.example.securityservice.requests.SignInRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    public static Logger logger = LogManager.getLogger(AuthorizationService.class);
    private final UserService userService;
    private final JwtTokensService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void registrationUser(Register register) {
        try {
            User usernameInUser = userRepository.findByUsername(register.getUsername());
            if (usernameInUser == null) {
                BigDecimal balance = BigDecimal.valueOf(1000);
                User user = new User();
                user.setUsername(register.getUsername());
                logger.info(register.getUsername());
                user.setPassword(passwordEncoder.encode(register.getPassword()));
                logger.info(register.getPassword());
                user.setRoles(Collections.singleton(RoleEnum.ROLE_USER));
                user.setBalance(balance);
                userRepository.save(user);
                logger.info(register.getUsername() + "Done");

            } else logger.info("Username already in use");
        }
        catch (Exception e) {
            logger.error(e);
        }
    }

    @Transactional
    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            signInRequest.getUsername(),
            signInRequest.getPassword()
        ));

    var user = userService.getUserDetailsService().loadUserByUsername(signInRequest.getUsername());

    String jwtAccess = jwtService.generateAccessToken(user);
    String jwtRefresh = jwtService.generateRefreshToken(user);

return new JwtAuthenticationResponse(jwtAccess, jwtRefresh);
    }


}
