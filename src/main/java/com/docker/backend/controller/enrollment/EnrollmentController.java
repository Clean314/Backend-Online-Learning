package com.docker.backend.controller.enrollment;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.CourseDTO;
import com.docker.backend.dto.EnrollmentCourseDTO;
import com.docker.backend.entity.user.Student;
import com.docker.backend.service.course.CourseService;
import com.docker.backend.service.enrollment.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students/enrollments")
@PreAuthorize("hasRole('STUDENT')")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final CourseService courseService;
    private final AuthUtil authUtil;

    @GetMapping
    public ResponseEntity<List<EnrollmentCourseDTO>> getMyEnrollments(Authentication authentication) {
        Student student = authUtil.getStudent(authentication);
        return ResponseEntity.ok(enrollmentService.getEnrolledCourses(student));
    }

    @PostMapping("/{courseId}")
    public ResponseEntity<Void> enroll(Authentication authentication,
                                       @PathVariable Long courseId) {
        Student student = authUtil.getStudent(authentication);
        enrollmentService.enroll(student, courseId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> cancel(Authentication authentication,
                                       @PathVariable Long courseId) {
        Student student = authUtil.getStudent(authentication);
        enrollmentService.cancelEnroll(student, courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<EnrollmentCourseDTO>> getAvailableCourses(Authentication authentication) {
        Student student = authUtil.getStudent(authentication);
        return ResponseEntity.ok(enrollmentService.getAllEnrollmentCourses(student));
    }

    @GetMapping("/all-courses")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }
}
