package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.LessonRequest;
import org.example.dto.LessonResponseForAdmin;
import org.example.dto.LessonResponseForClient;
import org.example.dto.mapping.LessonMapping;
import org.example.exception.InstructorNotFoundException;
import org.example.exception.LessonNotFoundException;
import org.example.exception.LimitedPlacesException;
import org.example.exception.UserNotFoundException;
import org.example.model.Horse;
import org.example.model.Lesson;
import org.example.enums.LessonStatus;
import org.example.model.UserRegistration;
import org.example.repository.HorseRepository;
import org.example.repository.LessonsRepository;
import org.example.repository.UserDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonService {


    private final LessonsRepository lessonsRepository;

    private final UserDataRepository userDataRepository;

    private final HorseRepository horseRepository;

    private final LessonMapping lessonMapping;

    private static final int HORSES_FOR_INSTRUCTOR = 1;

    @Transactional(readOnly = true)
    public List<LessonResponseForAdmin> getAllLessons() {
        List<Lesson> lessons = lessonsRepository.findAll();
        return lessons.stream().map(lessonMapping::toResponseForAdmin).toList();
    }

    @Transactional(readOnly = true)
    public LessonResponseForAdmin getLessonById(Long id) {
        return lessonsRepository.findById(id)
                .map(lessonMapping::toResponseForAdmin)
                .orElseThrow(() ->
                        new LessonNotFoundException("Lesson not found with id " + id)
                );
    }

    @Transactional
    public LessonResponseForAdmin createLesson(LessonRequest lessonRequest) {
        Lesson lesson = lessonMapping.toEntity(lessonRequest);
        lessonsRepository.save(lesson);
        return lessonMapping.toResponseForAdmin(lesson);
    }

    @Transactional
    public LessonResponseForAdmin changeHorses(Long lessonId, List<Long> horsesId) {
        Lesson currentLesson = lessonsRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson with id " + lessonId + " not found"));
        int currentCountParticipants = currentLesson.getParticipants().size();
        int updateCountHorses = horsesId.size() - HORSES_FOR_INSTRUCTOR;
        if (currentCountParticipants != updateCountHorses) {
            throw new IllegalArgumentException("Number of participants does not match the number of horses");
        }
        List<Horse> horses = lessonMapping.horseIdsToEntity(horsesId);
        currentLesson.setHorses(horses);
        lessonsRepository.save(currentLesson);
        return lessonMapping.toResponseForAdmin(currentLesson);
    }

    @Transactional
    public LessonResponseForAdmin changeMaxParticipants(Long lessonId, Integer maxParticipants) {
        Lesson currentLesson = lessonsRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found with id " + lessonId));
        int currentCount = currentLesson.getParticipants().size();
        if (maxParticipants < currentCount) {
            throw new IllegalArgumentException("You can't reduce the space, it's already been recorded " + currentCount);
        }
        currentLesson.setMaxParticipants(maxParticipants);
        lessonsRepository.save(currentLesson);
        return lessonMapping.toResponseForAdmin(currentLesson);
    }

    @Transactional
    public LessonResponseForAdmin changeInstructor(Long lessonId, Long instructorId) {
        Lesson currentLesson = lessonsRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found with id " + lessonId));
        UserRegistration instructor = userDataRepository.findById(instructorId)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor with id " + instructorId + " not found"));
        currentLesson.setInstructor(instructor);
        lessonsRepository.save(currentLesson);
        return lessonMapping.toResponseForAdmin(currentLesson);
    }

    @Transactional
    public LessonResponseForAdmin changeStatus(Long lessonId, LessonStatus status) {
        Lesson currentLesson = lessonsRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found with id " + lessonId));
        currentLesson.setStatus(status);
        lessonsRepository.save(currentLesson);
        return lessonMapping.toResponseForAdmin(currentLesson);
    }

    @Transactional
    public void deleteLesson(Long lessonId) {
        if (!lessonsRepository.existsById(lessonId)) {
            throw new LessonNotFoundException("Lesson not found with id " + lessonId);
        }
        lessonsRepository.deleteById(lessonId);
    }

    @Transactional
    public LessonResponseForClient enrollParticipant(Long lessonId, Long userId){
        UserRegistration userData = userDataRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException("User with id "+userId+" not found"));
        Lesson lesson = lessonsRepository.findById(lessonId)
                .orElseThrow(()->new LessonNotFoundException("Lesson not found with id " + lessonId));

        int countParticipants = lesson.getParticipants().size();
        int maxCountParticipants = lesson.getMaxParticipants();
        if(countParticipants+1 > maxCountParticipants){
            throw new LimitedPlacesException("There are no more places for this class");
        }
        if (lesson.getStartTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot enroll in past lesson");
        }
        List<UserRegistration> currantParticipant = lesson.getParticipants();
        if(currantParticipant.contains(userData)){
            throw new IllegalArgumentException("You are already enrolled in this lesson");
        }
        currantParticipant.add(userData);
        lesson.setParticipants(currantParticipant);
        lessonsRepository.save(lesson);
        return lessonMapping.toResponseForClient(lesson);
    }

    @Transactional(readOnly = true)
    public List<LessonResponseForAdmin> getAllLessonsByInstructorForAdmin(Long id){
        List<Lesson> lessons = lessonsRepository.findByInstructorId(id);
        return lessons.stream()
                .map(lessonMapping::toResponseForAdmin)
                .toList();
    }
    @Transactional(readOnly = true)
    public List<LessonResponseForClient> getAllLessonsByInstructorForClient(Long id){
        List<Lesson> lessons = lessonsRepository.findByInstructorId(id);
        return lessons.stream()
                .map(lessonMapping::toResponseForClient)
                .toList();
    }

    @Transactional
    public List<LessonResponseForAdmin> getFutureLessonsByInstructorForAdmin(Long id){
        return lessonsRepository.findByInstructorIdAndStartTimeAfter(id, LocalDateTime.now())
                .stream()
                .map(lessonMapping::toResponseForAdmin)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LessonResponseForClient> getFutureLessonsByInstructorForClient(Long id){
        return lessonsRepository.findByInstructorIdAndStartTimeAfter(id, LocalDateTime.now())
                .stream()
                .map(lessonMapping::toResponseForClient)
                .toList();
    }


}

