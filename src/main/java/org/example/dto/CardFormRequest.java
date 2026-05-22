package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CardFormRequest {

    @NotNull(message = "Введите номер карты")
    private String cardNumber;

    @NotNull(message = "Введите срок действия карты")
    private String cardExpiry;

    @NotNull(message = "Введите CVC")
    private String cardCvc;
}
