package com.docker.backend.repository.course;

import com.docker.backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByEducatorId(Long id);
}