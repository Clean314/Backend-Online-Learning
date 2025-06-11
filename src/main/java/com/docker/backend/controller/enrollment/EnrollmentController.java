package com.docker.backend.controller.enrollment;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.course.CourseDTO;
import com.docker.backend.dto.enrollment.EnrollmentCourseDTO;
import com.docker.backend.entity.user.Student;
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
    private final AuthUtil authUtil;

    @GetMapping
    public ResponseEntity<List<EnrollmentCourseDTO>> getAllCourses(Authentication authentication) {
        Student student = authUtil.getStudent(authentication);
        return ResponseEntity.ok(enrollmentService.getAllEnrollmentCourses(student));
    }

    @GetMapping("/my")
    public ResponseEntity<List<EnrollmentCourseDTO>> getMyEnrollments(Authentication authentication) {
        Student student = authUtil.getStudent(authentication);
        return ResponseEntity.ok(enrollmentService.getEnrolledCourses(student));
    }

    @GetMapping("/course-id/{courseId}")
    public ResponseEntity<CourseDTO> getEnrolledCourseById(Authentication authentication,
                                                           @PathVariable("courseId") Long courseId) {
        Student student = authUtil.getStudent(authentication);
        return ResponseEntity.ok(enrollmentService.getEnrolledCourseById(student, courseId));
    }

    @PostMapping("/course-id/{courseId}") // path 변수 url
    public ResponseEntity<Void> enroll(Authentication authentication,
                                       @PathVariable("courseId") Long courseId) { // @PathVariable 은 url 에서 path 로 들어온 데이터를 가져옴. ex) /enrollments/1
        Student student = authUtil.getStudent(authentication);
        enrollmentService.enroll(student, courseId); // url 로 들어온 courseId 값을 이용한다
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/course-id/{courseId}")
    public ResponseEntity<Void> cancel(Authentication authentication,
                                       @PathVariable("courseId") Long courseId) {
        Student student = authUtil.getStudent(authentication);
        enrollmentService.cancelEnroll(student, courseId);
        return ResponseEntity.noContent().build();
    }

}
