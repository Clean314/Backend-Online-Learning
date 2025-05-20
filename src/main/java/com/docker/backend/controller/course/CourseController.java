package com.docker.backend.controller.course;

import com.docker.backend.dto.CourseCreateRequest;
import com.docker.backend.entity.Course;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/learn/course")
@PreAuthorize("hasRole('EDUCATOR')")
public class CourseController {

    private final CourseService courseService;

     @GetMapping("/my-courses")
     public ResponseEntity<List<Course>> getMyCourses(@AuthenticationPrincipal Educator educator) {
         return ResponseEntity.ok(courseService.getCoursesByEducator(educator));
     }

    @PostMapping
    public ResponseEntity<Void> createCourse(@AuthenticationPrincipal Educator educator,
                                             @RequestBody CourseCreateRequest req) {
        courseService.createCourse(educator, req);
        return ResponseEntity.ok().build();
    }


}
