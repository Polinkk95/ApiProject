package org.example.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.LessonStatus;
import org.example.enums.LessonType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LessonRequest {

    private List<Long> horses = new ArrayList<>();

    @NotNull(message="Тип занятия обязателен")
    private LessonType type;

    @NotNull(message = "Время начала обязательно")
    @Future(message = "Время начала должно быть в будущем")
    private LocalDateTime startTime;

    @NotNull(message = "Время окончания обязательно")
    @Future(message = "Время окончания должно быть в будущем")
    private LocalDateTime endTime;

    @NotNull(message="ID инструктора обязателен")
    @Positive(message="ID должно быть положительным")
    private Long instructorId;

    @NotNull(message = "Максимальное количество участников обязательно")
    @Positive(message = "Максимальное количество участников должно быть положительным")
    private Integer maxParticipants;

    private List<Long> participants = new ArrayList<>();

    @NotNull(message = "Статус занятия обязателен")
    private LessonStatus status;
}
