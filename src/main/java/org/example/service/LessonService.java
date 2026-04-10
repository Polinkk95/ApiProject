package org.example.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.LessonRequest;
import org.example.dto.LessonResponse;
import org.example.dto.mapping.LessonMapping;
import org.example.exception.HorseNotFoundException;
import org.example.exception.InstructorNotFoundException;
import org.example.exception.LessonNotFoundException;
import org.example.model.Horse;
import org.example.model.Instructor;
import org.example.model.Lesson;
import org.example.repository.HorseRepository;
import org.example.repository.InstructorRepository;
import org.example.repository.LessonsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class LessonService {

    private final LessonsRepository lessonsRepository;

    private final HorseRepository horseRepository;

    private final InstructorRepository instructorRepository;

    private final LessonMapping lessonMapping;

    @Transactional
    public List<LessonResponse> getAllLessons() {
        List<Lesson> lessons = lessonsRepository.findAll();
        return lessons.stream().map(lessonMapping::toResponse).toList();
    }

    @Transactional
    public LessonResponse getLessonById(Long id) {
        return lessonsRepository.findById(id)
                .map(lessonMapping::toResponse)
                .orElseThrow(() ->
                        new LessonNotFoundException("Lesson not found with id " + id)
                );
    }

    @Transactional
    public LessonResponse createLesson(LessonRequest lessonRequest) {
        Lesson lesson = lessonMapping.toEntity(lessonRequest);
        lessonsRepository.save(lesson);
        return lessonMapping.toResponse(lesson);
    }

    @Transactional
    public LessonResponse update(Long id, LessonRequest lessonRequestDetails) {
        return lessonsRepository.findById(id)
                .map(lesson ->
                {
                    Long horseId = lessonRequestDetails.getHorseId();
                    Long instructorId = lessonRequestDetails.getInstructorId();
                    Horse horse = horseRepository.findById(horseId).orElseThrow(() ->
                            new HorseNotFoundException("Horse not found with id: " + horseId)
                    );
                    Instructor instructor = instructorRepository.findById(instructorId)
                            .orElseThrow(() ->
                                    new InstructorNotFoundException("Instructor not found with id " + instructorId));
                    lesson.setHorse(horse);
                    lesson.setInstructor(instructor);
                    lesson.setRoute(lessonRequestDetails.getRoute());
                    lessonsRepository.save(lesson);
                    return lessonMapping.toResponse(lesson);
                })
                .orElseThrow(() -> new IllegalArgumentException
                        ("Failed to update information about the lesson with id " + id));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteLesson(Long id) {
        if (!lessonsRepository.existsById(id)) {
            throw new LessonNotFoundException("Lesson not found with id " + id);
        }
        lessonsRepository.deleteById(id);
    }
}

