package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.LessonStatus;
import org.example.enums.LessonType;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LessonResponseForClient {

    private Long id;

    private LessonType type;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private UserRegistrationResponse instructor;

    private Integer maxParticipants;

    private Integer currentParticipants;

    private LessonStatus status;
}
