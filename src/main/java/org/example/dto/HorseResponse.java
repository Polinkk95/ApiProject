package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.Breed;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class HorseResponse {

    private Long id;

//    private UUID registrationNumber;

    private String name;

    private Breed breed;

    private Integer age;
}
