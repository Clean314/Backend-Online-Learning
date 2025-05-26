package com.docker.backend.repository.enrollment;

import com.docker.backend.entity.Course;
import com.docker.backend.entity.Enrollment;
import com.docker.backend.entity.user.Student;
import com.docker.backend.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentAndCourse(Student student, Course course);

    long countByCourseAndStatus(Course course, Status status);

    Optional<Enrollment> findByStudentAndCourseId(Student student, Long courseId);

    List<Enrollment> findByStudentAndStatus(Student student, Status status);

    List<Enrollment> findByStudent(Student student);

}
