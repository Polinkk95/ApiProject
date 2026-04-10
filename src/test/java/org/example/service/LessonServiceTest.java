package org.example.service;

import org.example.dto.LessonRequest;
import org.example.dto.LessonResponse;
import org.example.dto.mapping.LessonMapping;
import org.example.exception.LessonNotFoundException;
import org.example.model.Breed;
import org.example.model.Horse;
import org.example.model.Instructor;
import org.example.model.Lesson;
import org.example.model.Route;
import org.example.repository.HorseRepository;
import org.example.repository.InstructorRepository;
import org.example.repository.LessonsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    LessonsRepository lessonsRepository;

    @Mock
    HorseRepository horseRepository;

    @Mock
    InstructorRepository instructorRepository;

    @Mock
    LessonMapping lessonMapping;

    @InjectMocks
    LessonService service;

    @Test
    public void getLessonById_ThrowLessonNotFoundException() {
        long notExistId = 999L;

        when(lessonsRepository.findById(notExistId)).thenReturn(Optional.empty());

        LessonNotFoundException exception = assertThrows(LessonNotFoundException.class,
                () -> service.getLessonById(notExistId));
    }

    @Test
    public void getLessonById_ReturnResponse() {
        long lessonID = 12L;

        Horse horse = new Horse(5L, "Spirit", Breed.AKHAL_TEKE, 10);
        Instructor instructor = new Instructor(4L, "Bob", "Wild", 23, 2);
        Route route = Route.COMPETITION_PREP;

        Lesson lesson = new Lesson(lessonID, horse, instructor, route);

        Long horseID = horse.getId();
        Long instructorID = instructor.getId();

        LessonResponse expectedResponse = new LessonResponse(lessonID, horseID, instructorID, route);

        when(lessonsRepository.findById(lessonID)).thenReturn(Optional.of(lesson));
        when(lessonMapping.toResponse(lesson)).thenReturn(expectedResponse);

        LessonResponse actual = service.getLessonById(lessonID);

        assertNotNull(actual);
        assertEquals(expectedResponse, actual);

        verify(lessonsRepository).findById(lessonID);
        verify(lessonMapping).toResponse(lesson);
    }

    @Test
    public void getAllLessons_ReturnTwoLessons() {
        long id1 = 1L;
        long id2 = 2L;

        Horse horse = new Horse(5L, "Spirit", Breed.AKHAL_TEKE, 10);
        Instructor instructor = new Instructor(4L, "Bob", "Wild", 23, 2);
        Route route = Route.COMPETITION_PREP;

        Lesson lesson1 = new Lesson(id1, horse, instructor, route);
        Lesson lesson2 = new Lesson(id2, horse, instructor, route);

        Long horseID = horse.getId();
        Long instructorID = instructor.getId();

        LessonResponse lessonsResponse1 = new LessonResponse(id1, horseID, instructorID, route);
        LessonResponse lessonsResponse2 = new LessonResponse(id2, horseID, instructorID, route);

        List<Lesson> lessons = List.of(lesson1, lesson2);

        when(lessonsRepository.findAll()).thenReturn(lessons);
        when(lessonMapping.toResponse(lesson1)).thenReturn(lessonsResponse1);
        when(lessonMapping.toResponse(lesson2)).thenReturn(lessonsResponse2);

        List<LessonResponse> result = service.getAllLessons();

        assertEquals(lessons.size(), result.size());
        assertEquals(lessonsResponse1, result.get(0));
        assertEquals(lessonsResponse2, result.get(1));

        verify(lessonsRepository).findAll();
        verify(lessonMapping).toResponse(lesson1);
        verify(lessonMapping).toResponse(lesson2);
    }

    @Test
    public void getAllLessons_ReturnEmpty() {
        List<Lesson> lessons = new ArrayList<>();

        when(lessonsRepository.findAll()).thenReturn(lessons);

        List<LessonResponse> result = service.getAllLessons();

        assertEquals(lessons.size(), result.size());

        verify(lessonsRepository).findAll();
        verify(lessonMapping, never()).toResponse(any());
    }

    @Test
    public void createLesson_Success() {
        long lessonID = 1L;

        Horse horse = new Horse(5L, "Spirit", Breed.AKHAL_TEKE, 10);
        Instructor instructor = new Instructor(4L, "Bob", "Wild", 23, 2);
        Route route = Route.COMPETITION_PREP;

        Long horseID = horse.getId();
        Long instructorID = instructor.getId();

        LessonRequest request = new LessonRequest(lessonID, horseID, instructorID, route);

        Lesson lesson = new Lesson(lessonID, horse, instructor, route);

        LessonResponse response = new LessonResponse(lessonID, horseID, instructorID, route);

        when(lessonMapping.toEntity(request)).thenReturn(lesson);
        when(lessonMapping.toResponse(lesson)).thenReturn(response);
        when(lessonsRepository.save(lesson)).thenReturn(lesson);

        LessonResponse result = service.createLesson(request);

        assertNotNull(result);
        assertEquals(result, response);

        verify(lessonMapping).toEntity(request);
        verify(lessonMapping).toResponse(lesson);
        verify(lessonsRepository).save(lesson);
    }

    @Test
    public void update_Success() {
        long lessonID = 1L;

        Horse horse = new Horse(5L, "Spirit", Breed.AKHAL_TEKE, 10);
        Instructor instructor = new Instructor(4L, "Bob", "Wild", 23, 2);
        Route route = Route.COMPETITION_PREP;

        Horse detailsHorse = new Horse(15L, "Wind", Breed.AKHAL_TEKE, 6);
        Instructor detailsInstructor = new Instructor(12L, "Sam", "Tomson", 52, 20);
        Route detailsRoute = Route.COMPETITION_PREP;

        Long detailsHorseID = detailsHorse.getId();
        Long detailsInstructorID = detailsInstructor.getId();

        LessonRequest details = new LessonRequest(lessonID, detailsHorseID, detailsInstructorID, detailsRoute);

        Lesson lesson = new Lesson(lessonID, horse, instructor, route);

        LessonResponse response = new LessonResponse(lessonID, detailsHorseID, detailsInstructorID, detailsRoute);

        when(lessonsRepository.findById(lessonID)).thenReturn(Optional.of(lesson));
        when(horseRepository.findById(detailsHorseID)).thenReturn(Optional.of(detailsHorse));
        when(instructorRepository.findById(detailsInstructorID)).thenReturn(Optional.of(detailsInstructor));

        when(lessonsRepository.save(lesson)).thenReturn(lesson);
        when(lessonMapping.toResponse(lesson)).thenReturn(response);

        LessonResponse result = service.update(lessonID, details);

        assertNotNull(result);
        assertEquals(lessonID, result.getId());
        assertEquals(result.getHorseId(), detailsHorseID);
        assertEquals(result.getInstructorId(), detailsInstructorID);
        assertEquals(detailsRoute, result.getRoute());

        verify(lessonsRepository).findById(lessonID);
        verify(horseRepository).findById(detailsHorseID);
        verify(instructorRepository).findById(detailsInstructorID);
        verify(lessonsRepository).save(lesson);
        verify(lessonMapping).toResponse(lesson);
    }

    @Test
    public void deleteLesson_SuccessfulDeletion() {
        long lessonID = 1L;

        when(lessonsRepository.existsById(lessonID)).thenReturn(Boolean.TRUE);
        doNothing().when(lessonsRepository).deleteById(lessonID);

        service.deleteLesson(lessonID);

        verify(lessonsRepository).existsById(lessonID);
        verify(lessonsRepository).deleteById(lessonID);
    }

    @Test
    public void deleteLesson_ThrowException() {
        long lessonID = 1L;

        when(lessonsRepository.existsById(lessonID)).thenReturn(false);

        assertThrows(LessonNotFoundException.class, () -> service.deleteLesson(lessonID));

        verify(lessonsRepository).existsById(lessonID);
        verify(lessonsRepository, never()).deleteById(lessonID);
    }
}