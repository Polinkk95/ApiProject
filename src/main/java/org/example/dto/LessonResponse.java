package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.model.Route;

@Getter
@Setter
@AllArgsConstructor
public class LessonResponse {

    private Long id;

    private Long horseId;

    private Long instructorId;

    private Route route;
}
