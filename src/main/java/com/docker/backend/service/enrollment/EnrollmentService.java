package com.docker.backend.service.enrollment;

import com.docker.backend.dto.course.CourseDTO;
import com.docker.backend.dto.enrollment.EnrollmentCourseDTO;
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
        Course course = courseRepository.findByIdForUpdate(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new IllegalArgumentException("Already enrolled.");
        }

        if (course.getAvailableEnrollment() <= 0) {
            throw new IllegalStateException("Course is full.");
        }

        Enrollment enrollment = new Enrollment(student, course, Status.ENROLLED);
        course.setAvailableEnrollment(course.getAvailableEnrollment() - 1);
        courseRepository.save(course);
        enrollmentRepository.save(enrollment);
    }

    public void cancelEnroll(Student student, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentAndCourseId(student, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found"));

        if (enrollment.getStatus() == Status.COMPLETED) {
            return;
        }
        courseRepository.findById(enrollment.getCourse().getId())
                .ifPresent(course -> {
                    course.setAvailableEnrollment(course.getAvailableEnrollment() + 1);
                    courseRepository.save(course);
                });
        enrollmentRepository.delete(enrollment);
    }

    public List<EnrollmentCourseDTO> getEnrolledCourses(Student student) {
        return mapToDTO(enrollmentRepository.findByStudent(student));
    }

    public List<EnrollmentCourseDTO> getAllEnrollmentCourses(Student student) {
        Map<Long, Status> statusMap = enrollmentRepository.findByStudent(student).stream()
                .collect(Collectors.toMap(
                        e -> e.getCourse().getId(),
                        Enrollment::getStatus
                ));

        return courseRepository.findAll().stream()
                .map(course -> {
                    Status status = statusMap.getOrDefault(course.getId(), Status.AVAILABLE);
                    long enrolled = enrollmentRepository.countByCourseAndStatus(course, (Status.ENROLLED));

                    return new EnrollmentCourseDTO(
                            course.getId(),
                            course.getCourseName(),
                            course.getEducator().getName(),
                            course.getCategory(),
                            course.getDifficulty(),
                            status,
                            course.getPoint(),
                            course.getMaxEnrollment(),
                            course.getAvailableEnrollment()
                    );
                })
                .collect(Collectors.toList());
    }

    public CourseDTO getEnrolledCourseById(Student student, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentAndCourseId(student, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found"));

        Course course = enrollment.getCourse();
        Educator educator = course.getEducator();
        long enrolled = enrollmentRepository.countByCourseAndStatus(course, Status.ENROLLED);

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


    private List<EnrollmentCourseDTO> mapToDTO(List<Enrollment> enrollments) { // Enrollment -> EnrollmentCourseDTO 로 변환하는 메서드
        return enrollments.stream().map(enrollment -> {
            Course course = enrollment.getCourse();
            Educator educator = course.getEducator();
            long enrolled = enrollmentRepository.countByCourseAndStatus(course, Status.ENROLLED);

            return new EnrollmentCourseDTO(
                    course.getId(),
                    course.getCourseName(),
                    educator.getName(),
                    course.getCategory(),
                    course.getDifficulty(),
                    enrollment.getStatus(),
                    course.getPoint(),
                    course.getMaxEnrollment(),
                    course.getAvailableEnrollment()
            );
        }).collect(Collectors.toList());
    }

    public Integer getCountByCourseId(Long courseId) {
        return enrollmentRepository.countByCourseId(courseId);
    }
}
