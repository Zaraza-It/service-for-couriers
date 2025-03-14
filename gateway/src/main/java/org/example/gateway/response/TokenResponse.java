package org.example.gateway.response;

import lombok.Data;

@Data
public class TokenResponse {
    public String accessToken;
    public String refreshToken;
}
