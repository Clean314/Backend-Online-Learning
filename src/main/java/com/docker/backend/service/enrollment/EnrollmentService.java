package com.docker.backend.service.enrollment;

import com.docker.backend.dto.EnrollmentCourseDTO;
import com.docker.backend.entity.Course;
import com.docker.backend.entity.Enrollment;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.entity.user.Student;
import com.docker.backend.enums.Status;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.repository.enrollment.EnrollmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    public void enroll(Student student, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new IllegalArgumentException("Already enrolled.");
        }

        enrollmentRepository.save(new Enrollment(student, course, Status.ENROLLED));
    }

    @Transactional
    public void cancelEnroll(Student student, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentAndCourseId(student, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found"));
        enrollment.setStatus(Status.WITHDRAWN);
    }

    public List<EnrollmentCourseDTO> getEnrolledCourses(Student student) {
        return mapToDTO(enrollmentRepository.findByStudentAndStatus(student, Status.ENROLLED));
    }

    public List<EnrollmentCourseDTO> getCompletedCourses(Student student) {
        return mapToDTO(enrollmentRepository.findByStudentAndStatus(student, Status.COMPLETED));
    }

    public List<EnrollmentCourseDTO> getAllEnrollmentCourses(Student student) {
        Map<Long, Status> statusMap = enrollmentRepository.findByStudent(student).stream()
                .collect(Collectors.toMap(
                        e -> e.getCourse().getId(),
                        Enrollment::getStatus


                ));

        return courseRepository.findAll().stream()
                .map(course -> {
                    Status status = statusMap.get(course.getId());
                    Status courseStatus = (status != null) ? status : Status.AVAILABLE;

                    return new EnrollmentCourseDTO(
                            course.getCourseName(),
                            course.getEducator().getName(),
                            course.getCategory(),
                            course.getDifficulty(),
                            courseStatus
                    );
                })
                .collect(Collectors.toList());
    }

    private List<EnrollmentCourseDTO> mapToDTO(List<Enrollment> enrollments) {
        return enrollments.stream().map(enrollment -> {
            Course course = enrollment.getCourse();
            Educator educator = course.getEducator();
            return new EnrollmentCourseDTO(
                    course.getCourseName(),
                    educator.getName(),
                    course.getCategory(),
                    course.getDifficulty(),
                    enrollment.getStatus()
            );
        }).collect(Collectors.toList());
    }
}
