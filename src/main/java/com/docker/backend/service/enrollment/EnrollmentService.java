package com.docker.backend.service.enrollment;

import com.docker.backend.dto.course.CourseDTO;
import com.docker.backend.dto.enrollment.EnrollmentCourseDTO;
import com.docker.backend.domain.course.Course;
import com.docker.backend.domain.enrollment.Enrollment;
import com.docker.backend.domain.user.Educator;
import com.docker.backend.domain.user.Student;
import com.docker.backend.domain.enums.Status;
import com.docker.backend.exception.GlobalExceptionHandler;
import com.docker.backend.mapper.enrollment.EnrollmentCourseMapper;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.repository.enrollment.EnrollmentRepository;
import com.docker.backend.service.VerifyService;
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

    private final VerifyService verifyService;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    private EnrollmentCourseMapper enrollmentCourseMapper;

    public void enroll(Student student, Long courseId) {
        Course course = verifyService.isExistCourse(courseId);
        verifyService.isNotEnrolled(student.getId(), courseId);
        isAvailableEnrollment(course);

        course.setAvailableEnrollment(course.getAvailableEnrollment() - 1);
        courseRepository.save(course);

        Enrollment enrollment = new Enrollment(student, course, Status.ENROLLED);
        enrollmentRepository.save(enrollment);
    }

    public void cancelEnroll(Student student, Long courseId) {
        Enrollment enrollment = verifyService.isEnrolled(student.getId(), courseId);
        if (enrollment.getStatus() == Status.COMPLETED) {
            return;
        }

        Course course = verifyService.isExistCourse(courseId);
        course.setAvailableEnrollment(course.getAvailableEnrollment() + 1);
        courseRepository.save(course);

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
                            course.getAvailableEnrollment(),
                            course.getDescription()
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

    private List<EnrollmentCourseDTO> mapToDTO(List<Enrollment> enrollments) {
        return enrollments.stream().map(enrollment -> {
            Course course = enrollment.getCourse();
            Educator educator = course.getEducator();

            return new EnrollmentCourseDTO(
                    course.getId(),
                    course.getCourseName(),
                    educator.getName(),
                    course.getCategory(),
                    course.getDifficulty(),
                    enrollment.getStatus(),
                    course.getPoint(),
                    course.getMaxEnrollment(),
                    course.getAvailableEnrollment(),
                    course.getDescription()
            );
        }).collect(Collectors.toList());
    }

    private void isAvailableEnrollment(Course course){
        if (course.getAvailableEnrollment() <= 0) {
            throw new GlobalExceptionHandler.EnrollmentUnavailableException();
        }
    }
}
