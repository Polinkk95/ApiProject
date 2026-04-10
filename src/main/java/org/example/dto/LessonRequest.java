package org.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.model.Route;

@Getter
@Setter
@AllArgsConstructor
public class LessonRequest {

    private Long id;

    @NotNull(message="ID лошади обязателен")
    @Positive(message="ID должно быть положительным")
    private Long horseId;

    @NotNull(message="ID лошади обязателен")
    @Positive(message="ID должно быть положительным")
    private Long instructorId;

    @NotNull(message="Маршрут должен быть задан")
    private Route route;
}
