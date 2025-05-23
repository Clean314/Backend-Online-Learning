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
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/my")
    public ResponseEntity<List<EnrollmentCourseDTO>> getMyEnrollments(Authentication authentication) {
        Student student = authUtil.getStudent(authentication);
        return ResponseEntity.ok(enrollmentService.getEnrolledCourses(student));
    }

    @PostMapping("/{courseId}") // path 변수 url
    public ResponseEntity<Void> enroll(Authentication authentication,
                                       @PathVariable Long courseId) { // @PathVariable 은 url 에서 path 로 들어온 데이터를 가져옴. ex) /enrollments/1 
        Student student = authUtil.getStudent(authentication);
        enrollmentService.enroll(student, courseId); // url로 들어온 courseId 값을 이용한다
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
    public ResponseEntity<List<CourseDTO>> getAvailableCourses(Authentication authentication) {
        Student student = authUtil.getStudent(authentication);
        return ResponseEntity.ok(enrollmentService.getEnableCourses(student));
    }

}
