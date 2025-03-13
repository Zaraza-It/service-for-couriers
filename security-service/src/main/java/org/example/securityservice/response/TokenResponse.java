package org.example.securityservice.response;

import lombok.*;
import org.springframework.lang.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    @Getter
    private String accessToken;
   @Nullable
    private String refreshToken;
}
