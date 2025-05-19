package com.docker.backend.service.enrollment;

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

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public void enroll(Student student, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("유효한 강의가 아닙니다."));

        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new IllegalArgumentException("이미 신청된 강의입니다.");
        }

        long enrolledCount = enrollmentRepository.countByCourseAndStatus(course, Status.ENROLLED);

        Status status = enrolledCount < course.getMaxEnrollment()
                ? Status.ENROLLED
                : Status.WAITING;

        enrollmentRepository.save(new Enrollment(student, course, status));
    }

    @Transactional
    public void cancelEnroll(Student student, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentAndCourseId(student, courseId)
                .orElseThrow(() -> new EntityNotFoundException("신청한 강의가 아닙니다."));

        enrollment.setStatus(Status.WITHDRAWN);
        enrollmentRepository.save(enrollment);
    }


    @Transactional
    public List<EnrollmentDto> getMyEnrollments(Student student) {
        return enrollmentRepo.findByStudent(student)
                .stream()
                .map(EnrollmentDto::from)
                .toList();
    }
}
