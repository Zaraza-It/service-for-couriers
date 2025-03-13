package org.example.authservice.requests;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
}
