package com.docker.backend.service.course;

import com.docker.backend.dto.course.CourseCreateDTO;
import com.docker.backend.dto.course.CourseDTO;
import com.docker.backend.entity.Course;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.service.enrollment.EnrollmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentService enrollmentService;

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

    public void updateCourse(Educator educator, Long courseId, CourseDTO req) {
        Course course = courseRepository.findByEducatorAndId(educator, courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (enrollmentService.getCountByCourseId(courseId) > 0) {
            throw new IllegalArgumentException("Cannot update course with existing enrollments");
        }

        course.setCourseName(req.getCourseName());
        course.setCategory(req.getCategory());
        course.setDifficulty(req.getDifficulty());
        course.setPoint(req.getPoint());
        course.setDescription(req.getDescription());
        course.setMaxEnrollment(req.getMaxEnrollment());
        courseRepository.save(course);
    }

    public void deleteCourse(Educator educator, Long courseId) {
        Course course = courseRepository.findByEducatorAndId(educator, courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (enrollmentService.getCountByCourseId(courseId) > 0) {
            throw new IllegalArgumentException("Cannot delete course with existing enrollments");
        }

        courseRepository.delete(course);
    }

    public Long createCourse(Educator educator, CourseCreateDTO req) {
        Course course = new Course();
        course.setEducator(educator);
        course.setCourseName(req.getCourseName());
        course.setCategory(req.getCategory());
        course.setDifficulty(req.getDifficulty());
        course.setPoint(req.getPoint());
        course.setDescription(req.getDescription());
        course.setMaxEnrollment(req.getMaxEnrollment());
        course.setAvailableEnrollment(req.getMaxEnrollment());
        return courseRepository.save(course).getId();
    }

    public CourseDTO getCourse(Educator educator, Long courseId) {
        Course course = courseRepository.findByEducatorAndId(educator, courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

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
    }
}
