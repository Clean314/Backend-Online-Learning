package com.docker.backend.service.course;

import com.docker.backend.dto.CourseDTO;
import com.docker.backend.entity.Course;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.repository.course.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> getMyCourses(Educator educator) {
        return courseRepository.findByEducatorId(educator.getId());
    }

    public ResponseEntity<Void> createCourse(Educator educator, CourseDTO req) {
        Course course = new Course();
        course.setCourseName(req.getCourseName());
        course.setCategory(req.getCategory());
        course.setDifficulty(req.getDifficulty());
        course.setPoint(req.getPoint());
        course.setEducator(educator);
        course.setDescription(req.getDescription());
        course.setMaxEnrollment(req.getMaxEnrollment());
        courseRepository.save(course);
        return ResponseEntity.ok().build();
    }
}
