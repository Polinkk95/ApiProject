package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.LessonStatus;
import org.example.enums.LessonType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LessonResponseForAdmin {

    private Long id;

    private List<HorseResponse> horses;

    private LessonType type;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private UserRegistrationResponse instructor;

    private Integer maxParticipants;

    private List<UserRegistrationResponse> participants;

    private LessonStatus status;
}
