package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginRequest {

    @NotNull(message="Почта обязательна для заполнения")
    @Email(message = "Неверный формат email")
    private String email;

    @NotNull(message="Введите пароль")
    private String password;
}
