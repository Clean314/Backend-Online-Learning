package com.docker.backend.repository.lecture;

import com.docker.backend.entity.course.Course;
import com.docker.backend.entity.lecture.Lecture;
import com.docker.backend.entity.lecture.LectureHistory;
import com.docker.backend.entity.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureHistoryRepository extends JpaRepository<LectureHistory, Long> {
    Optional<LectureHistory> findByStudentAndLecture(Student student, Lecture Lecture);
    List<LectureHistory> findAllByStudentId(Course courseId);
}
