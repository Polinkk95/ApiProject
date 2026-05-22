package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.Breed;
import org.example.enums.Grooming;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class HorseGroomingRequest {

    @NotNull(message="Регистрационный номер лошади нужно заполнить")
    private UUID registrationNumber;

    @NotNull(message="Имя лошади нужно заполнить")
    private String name;

    @NotNull(message="Породу лошади нужно заполнить")
    private Breed breed;

    @NotNull(message="Возраст лошади нужно указать")
    private Integer age;

    private List<Grooming> services;
}
