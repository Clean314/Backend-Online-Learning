package com.docker.backend.service.enrollment;

import com.docker.backend.dto.EnrollmentDTO;
import com.docker.backend.entity.Course;
import com.docker.backend.entity.Enrollment;
import com.docker.backend.entity.user.Student;
import com.docker.backend.enums.Status;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.repository.enrollment.EnrollmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

        long enrolledCount = enrollmentRepository.countByCourseAndStatus(course, Status.ENROLLED);
        Status status = enrolledCount < course.getMaxEnrollment() ? Status.ENROLLED : Status.WAITING;

        enrollmentRepository.save(new Enrollment(student, course, status));
    }

    @Transactional
    public void cancelEnroll(Student student, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentAndCourseId(student, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found"));
        enrollment.cancel();
    }

    public List<EnrollmentDTO> getMyEnrollments(Student student) {
        return enrollmentRepository.findByStudent(student).stream()
                .map(e -> new EnrollmentDTO(
                        e.getCourse().getCourseName(),
                        e.getCourse().getEducator().getName(),
                        e.getCourse().getPoint(),
                        e.getStatus()))
                .collect(Collectors.toList());
    }

}
