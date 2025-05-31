package com.docker.backend.repository;

import com.docker.backend.entity.Course;
import com.docker.backend.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Boolean existsByTitleAndCourse(String title, Course course);
    Boolean existsByVideoUrlAndCourse(String videoUrl,Course course);
}
