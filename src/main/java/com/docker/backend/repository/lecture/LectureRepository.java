package com.docker.backend.repository.lecture;

import com.docker.backend.entity.course.Course;
import com.docker.backend.entity.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Boolean existsByTitleAndCourse(String title, Course course);
    Boolean existsByVideoUrlAndCourse(String videoUrl,Course course);
    List<Lecture> findByCourseId(Long courseId);
    Optional<Lecture> findById(Long id);
    Boolean existsByTitleAndCourseAndIdNot(String title, Course course, Long lectureId);
    Boolean existsByVideoUrlAndCourseAndIdNot(String videoUrl, Course course, Long lectureId);
}
