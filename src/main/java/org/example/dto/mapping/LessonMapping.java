package org.example.dto.mapping;

import org.example.dto.LessonRequest;
import org.example.dto.LessonResponse;
import org.example.model.Horse;
import org.example.model.Instructor;
import org.example.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface LessonMapping {

    Lesson toEntity(LessonRequest lessonDto);

    @Mapping(source = "horse", target = "horseId", qualifiedByName = "horseToId")
    @Mapping(source = "instructor", target = "instructorId", qualifiedByName = "instructorToId")
    LessonResponse toResponse(Lesson lesson);

    @Named("horseToId")
    default Long horseToId(Horse horse) {
        return horse != null ? horse.getId() : null;
    }

    @Named("instructorToId")
    default Long instructorToId(Instructor instructor) {
        return instructor != null ? instructor.getId() : null;
    }
}