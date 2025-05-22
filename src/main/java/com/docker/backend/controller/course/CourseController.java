package com.docker.backend.controller.course;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.CourseDTO;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/educators/courses")
@PreAuthorize("hasRole('EDUCATOR')")
public class CourseController {

    private final CourseService courseService;
    private final AuthUtil authUtil;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/my")
    public ResponseEntity<List<CourseDTO>> getMyCourses(Authentication authentication) {
        Educator educator = authUtil.getEducator(authentication);
        return ResponseEntity.ok(courseService.getMyCourses(educator));
    }

    @PostMapping
    public ResponseEntity<Void> createCourse(Authentication authentication,
                                             @RequestBody CourseDTO req) {
        Educator educator = authUtil.getEducator(authentication);
        courseService.createCourse(educator, req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

