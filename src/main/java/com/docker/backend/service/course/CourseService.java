package com.docker.backend.service.course;

import com.docker.backend.dto.course.CourseCreateDTO;
import com.docker.backend.dto.course.CourseDTO;
import com.docker.backend.domain.course.Course;
import com.docker.backend.domain.user.Educator;
import com.docker.backend.dto.course.CourseUpdateDTO;
import com.docker.backend.mapper.course.CourseMapper;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.service.VerifyService;
import com.docker.backend.service.enrollment.EnrollmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentService enrollmentService;
    private final VerifyService verifyService;

    @Autowired
    private final CourseMapper courseMapper;

    public CourseDTO getCourse(Educator educator, Long courseId) {
        return courseMapper.toDTO(verifyService.isExistCourse(courseId));
    }

    public List<CourseDTO> getAllCourses() {
        return courseMapper.toListDTO(
                courseRepository.findAllByOrderByCreatedAtDesc());
    }

    public List<CourseDTO> getMyCourses(Educator educator) {
        return courseMapper.toListDTO(
                courseRepository.findByEducatorId(educator.getId()
        ));
    }

    public Long createCourse(Educator educator, CourseCreateDTO req) {
        return courseRepository.save(
                courseMapper.toEntity(educator.getId(), req)).getId();
    }

    public Long updateCourse(Educator educator, Long courseId, CourseUpdateDTO updateDTO) {
        verifyService.isOwnerOfCourse(educator.getId(), courseId);
        verifyService.isExistEnrollment(courseId);

        return courseRepository.save(
                courseMapper.toEntity(courseId, updateDTO)).getId();
    }

    public void deleteCourse(Educator educator, Long courseId) {
        Course course = verifyService.isOwnerOfCourse(educator.getId(), courseId);
        verifyService.isExistEnrollment(courseId);

        courseRepository.delete(course);
    }
}
