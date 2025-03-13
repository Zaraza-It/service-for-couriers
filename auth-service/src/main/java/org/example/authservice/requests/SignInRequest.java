package org.example.authservice.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInRequest {


    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;
}
