package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorRequest {

    private Long id;

    private String name;

    private String surname;

    private int age;

    private int experienceYears;
}
