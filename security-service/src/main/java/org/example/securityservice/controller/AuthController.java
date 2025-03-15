package org.example.securityservice.controller;

import lombok.RequiredArgsConstructor;

import org.example.securityservice.requests.JwtAuthenticationResponse;
import org.example.securityservice.requests.Register;
import org.example.securityservice.requests.SignInRequest;
import org.example.securityservice.response.TokenResponse;
import org.example.securityservice.services.AuthorizationService;
import org.example.securityservice.services.JwtTokensService;
import org.springframework.http.HttpHeaders;
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
        System.out.println(register);
        headers.add("Register", "Register");
        authorizationService.registrationUser(register);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest){
       JwtAuthenticationResponse  jwtTokens = authorizationService.signIn(signInRequest);
       return ResponseEntity.ok()
               .header("AccessToken", jwtTokens.getAccessToken())
               .header("RefreshToken", jwtTokens.getRefreshToken())
               .build();
    }

    @GetMapping("update")
    public ResponseEntity<TokenResponse> updateByRefreshToken(String refresh){
       TokenResponse tokenResponse =  jwtTokensService.updateAccessTokenByRefreshToken(refresh);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "http://localhost:7777/auth/login");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(tokenResponse);
    }

}
