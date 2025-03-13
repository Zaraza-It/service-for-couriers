package org.example.authservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private Long id;
    private String accessToken;
    private String refreshToken;
    private String loggedOut;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
