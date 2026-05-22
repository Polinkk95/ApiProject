package org.example.repository;

import org.example.dto.LessonResponseForAdmin;
import org.example.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonsRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByInstructorId(Long instructorId);

    List<Lesson> findByInstructorIdAndStartTimeAfter(Long instructorId, LocalDateTime now);
}
