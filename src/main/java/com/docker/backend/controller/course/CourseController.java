package com.docker.backend.controller.course;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.course.CourseCreateDTO;
import com.docker.backend.dto.course.CourseDTO;
import com.docker.backend.domain.user.Educator;
import com.docker.backend.dto.course.CourseUpdateDTO;
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
    private final AuthUtil authUtil; // Authentication(인증 정보)에서 사용자 정보 빼기 위한 커스텀 클래스

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/my")
    public ResponseEntity<List<CourseDTO>> getMyCourses(Authentication authentication) { // Authentication 은 요청 헤더에서 토큰을 받아오게 됨
        Educator educator = authUtil.getEducator(authentication); // 목적에 맞는 사용자 추출해오기
        return ResponseEntity.ok(courseService.getMyCourses(educator));
    }

    @GetMapping("/course-id/{courseId}")
    public ResponseEntity<CourseDTO> getCourse(Authentication authentication,
                                               @PathVariable("courseId") Long courseId) {
        Educator educator = authUtil.getEducator(authentication);
        return ResponseEntity.ok(courseService.getCourse(educator, courseId));
    }

    @PostMapping
    public ResponseEntity<Long> createCourse(Authentication authentication,
                                             @RequestBody CourseCreateDTO courseCreateDTO) { // @RequestBody 는 요청 body 에 json 형태로 들어온 데이터를 가져옴
        Educator educator = authUtil.getEducator(authentication);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.createCourse(educator, courseCreateDTO)); // 201 Created HTTP 상태 반환 (어떤 거는 안되어있을 수도 있는데 추후 만들 예정
    }

    @PatchMapping("/course-id/{courseId}")
    public ResponseEntity<Long> updateCourse(Authentication authentication,
                                             @PathVariable("courseId") Long courseId,
                                             @RequestBody CourseUpdateDTO req) {
        Educator educator = authUtil.getEducator(authentication);
        ;
        return ResponseEntity.ok(courseService.updateCourse(educator, courseId, req));
    }

    @DeleteMapping("/course-id/{courseId}")
    public ResponseEntity<Void> deleteCourse(Authentication authentication,
                                             @PathVariable("courseId") Long courseId) {
        Educator educator = authUtil.getEducator(authentication);
        courseService.deleteCourse(educator, courseId);
        return ResponseEntity.noContent().build();
    }

}