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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAllByOrderByCreatedAtDesc().stream().map(course -> {  // .findAll() -> 강의 등록일순으로 정렬로 바꿈
            Educator educator = course.getEducator();
            return new CourseDTO(
                    course.getId(),
                    course.getCourseName(),
                    educator.getName(),
                    course.getCategory(),
                    course.getDifficulty(),
                    course.getPoint(),
                    course.getDescription(),
                    course.getMaxEnrollment(),
                    course.getAvailableEnrollment()
            );
        }).collect(Collectors.toList());
    }

    public List<CourseDTO> getMyCourses(Educator educator) {
//        return courseRepository.findByEducatorId(educator.getId()).stream()
//                .map(CourseDTO::new)
//                .toList();

        return courseRepository.findAll().stream().map(course -> {
            return new CourseDTO(
                    course.getId(),
                    course.getCourseName(),
                    educator.getName(),
                    course.getCategory(),
                    course.getDifficulty(),
                    course.getPoint(),
                    course.getDescription(),
                    course.getMaxEnrollment(),
                    course.getAvailableEnrollment()
            );
        }).collect(Collectors.toList());
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
