package com.docker.backend.controller.enrollment;

import com.docker.backend.dto.EnrollmentCourseDTO;
import com.docker.backend.entity.user.Student;
import com.docker.backend.service.enrollment.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/learn/enrollment")
@PreAuthorize("hasRole('STUDENT')")
public class EnrollmentController {

     private final EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<List<EnrollmentCourseDTO>> getMyEnrollments(@AuthenticationPrincipal Student student) {
        return ResponseEntity.ok(enrollmentService.getEnrolledCourses(student));
    }

     @PostMapping("/{id}")
     public ResponseEntity<Void> enroll(@AuthenticationPrincipal Student student,
                                        @PathVariable Long courseId) {
         enrollmentService.enroll(student, courseId);
         return ResponseEntity.ok().build();
     }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@AuthenticationPrincipal Student student,
                                       @PathVariable Long courseId) {
        enrollmentService.cancelEnroll(student, courseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/courses")
    public ResponseEntity<List<EnrollmentCourseDTO>> getEnableCourses(@AuthenticationPrincipal Student student) {
        return ResponseEntity.ok(enrollmentService.getAllEnrollmentCourses(student));
    }
}
