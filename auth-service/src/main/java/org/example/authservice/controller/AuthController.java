package org.example.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.example.authservice.requests.JwtAuthenticationResponse;
import org.example.authservice.requests.Register;
import org.example.authservice.requests.SignInRequest;
import org.example.authservice.response.TokenResponse;
import org.example.authservice.services.AuthorizationService;
import org.example.authservice.services.JwtTokensService;
import org.hibernate.sql.Update;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthorizationService authorizationService;
    private final JwtTokensService jwtTokensService;

    @PostMapping("/registration")
    public ResponseEntity<Void> signUp(@RequestBody Register register){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Register", "Register");
        authorizationService.registrationUser(register);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest signInRequest){
        return authorizationService.signIn(signInRequest);
    }

    @GetMapping("update")
    public ResponseEntity<TokenResponse> updateByRefreshToken(String refresh){
       TokenResponse tokenResponse =  jwtTokensService.updateAccessTokenByRefreshToken(refresh);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "http://localhost:7777/auth/login");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(tokenResponse);
    }

}
