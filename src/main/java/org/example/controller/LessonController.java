package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.LessonRequest;
import org.example.dto.LessonResponseForAdmin;
import org.example.dto.LessonResponseForClient;
import org.example.enums.LessonStatus;
import org.example.service.LessonService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public List<LessonResponseForAdmin> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{lessonId}")
    public LessonResponseForAdmin getLessonById(@PathVariable Long lessonId) {
        return lessonService.getLessonById(lessonId);
    }

    @PostMapping
    public LessonResponseForAdmin createLesson(@Valid @RequestBody LessonRequest lessonRequest) {
        return lessonService.createLesson(lessonRequest);
    }

    @PatchMapping("/{lessonId}/horses")
    public LessonResponseForAdmin changeHorses(
            @PathVariable Long lessonId,
            @RequestBody @NotNull
            List<Long> horsesId) {
        return lessonService.changeHorses(lessonId, horsesId);
    }

    @PatchMapping("/{lessonId}/max-participants")
    public LessonResponseForAdmin changeMaxParticipants(
            @PathVariable Long lessonId,
            @RequestParam @NotNull Integer maxParticipants) {
        return lessonService.changeMaxParticipants(lessonId, maxParticipants);
    }

    @PatchMapping("/{lessonId}/instructor")
    public LessonResponseForAdmin changeInstructor(
            @PathVariable Long lessonId,
            @RequestParam @NotNull Long instructorId){
        return lessonService.changeInstructor(lessonId, instructorId);
    }


    @PatchMapping("/{lessonId}/status")
    public LessonResponseForAdmin changeStatus(
            @PathVariable Long lessonId,
            @RequestParam @NotNull LessonStatus status){
        return lessonService.changeStatus(lessonId, status);
    }

    @DeleteMapping("/{lessonId}")
    public void deleteLesson(@PathVariable Long lessonId) {
        lessonService.deleteLesson(lessonId);
    }

    @PostMapping("/{lessonId}/enroll")
    public LessonResponseForClient enrollParticipant(
            @PathVariable Long lessonId,
            @RequestParam @NotNull Long userId){
        return lessonService.enrollParticipant(lessonId, userId);
    }

    @GetMapping("/instructor/{instructorId}/admin")
    public List<LessonResponseForAdmin> getAllLessonsByInstructorForAdmin(
            @PathVariable Long instructorId){
        return lessonService.getAllLessonsByInstructorForAdmin(instructorId);
    }

    @GetMapping("/instructor/{instructorId}/client")
    public List<LessonResponseForClient> getAllLessonsByInstructorForClient(
            @PathVariable Long instructorId){
        return lessonService.getAllLessonsByInstructorForClient(instructorId);
    }

    @GetMapping("/instructor/{instructorId}/admin/future")
    public List<LessonResponseForAdmin> getFutureLessonsByInstructorForAdmin(
            @PathVariable Long instructorId){
        return lessonService.getFutureLessonsByInstructorForAdmin(instructorId);
    }

    @GetMapping("/instructor/{instructorId}/client/future")
    public List<LessonResponseForClient> getFutureLessonsByInstructorForClient(
            @PathVariable Long instructorId){
        return lessonService.getFutureLessonsByInstructorForClient(instructorId);
    }
}
