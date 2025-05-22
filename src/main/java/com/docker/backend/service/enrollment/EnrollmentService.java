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
@Transactional // 트랜잭션 처리 (수강신청은 인원이 몰린다고 생각했기 때문)
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    public void enroll(Student student, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("해당 강의를 찾을 수 없습니다."));

        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new IllegalArgumentException("이미 신청하신 강의입니다.");
        }

        enrollmentRepository.save(new Enrollment(student, course, Status.ENROLLED));
    }

    public void cancelEnroll(Student student, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentAndCourseId(student, courseId)
                .orElseThrow(() -> new EntityNotFoundException("수강 정보를 찾을 수 없습니다."));
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

    // Enrollment -> EnrollmentCourseDTO 로 변환하는 메서드
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
