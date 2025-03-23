package org.example.market.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RequestSettingsUser {
    @Min(4)
    private String username;
    @Email
    private String email;
}
