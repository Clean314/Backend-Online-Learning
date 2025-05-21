package com.docker.backend.controller.course;

import com.docker.backend.config.CustomUserDetails;
import com.docker.backend.dto.CourseDTO;
import com.docker.backend.entity.Course;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/learn/course")
@PreAuthorize("hasRole('EDUCATOR')")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/my-courses")
    public ResponseEntity<List<CourseDTO>> getMyCourses(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Educator educator = (Educator) userDetails.getMember();

        List<Course> courses = courseService.getMyCourses(educator);
        List<CourseDTO> response = courses.stream()
                .map(CourseDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Void> createCourse(Authentication authentication,
                                             @RequestBody CourseDTO req) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Educator educator = (Educator) userDetails.getMember();
        return courseService.createCourse(educator, req);
    }

}
