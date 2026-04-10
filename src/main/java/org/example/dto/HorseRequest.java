package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Breed;

@Getter
@Setter
public class HorseRequest {

    private Long id;

    private String name;

    private Breed breed;

    private int age;
}
