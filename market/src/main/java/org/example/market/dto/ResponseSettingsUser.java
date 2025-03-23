package org.example.market.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ResponseSettingsUser {
    @Email
    private String email;
    @Min(4)
    private String username;
    private String role;
}
