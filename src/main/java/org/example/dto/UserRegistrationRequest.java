package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegistrationRequest {

    @NotNull(message="Имя обязательно для заполнения")
    private String firstName;

    @NotNull(message="Фамилия обязательна для заполнения")
    private String lastName;

    @NotNull(message="Почта обязательна для заполнения")
    @Email(message = "Неверный формат email")
    private String email;

    @NotNull(message="Введите пароль")
    private String password;

    @NotNull(message="Телефон обязателен для заполнения")
    private String phoneNumber;
}
