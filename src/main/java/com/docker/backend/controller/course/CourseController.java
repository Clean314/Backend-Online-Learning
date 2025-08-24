package com.docker.backend.controller.course;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.course.CourseCreateDTO;
import com.docker.backend.dto.course.CourseDTO;
import com.docker.backend.domain.user.Educator;
import com.docker.backend.dto.course.CourseUpdateDTO;
import com.docker.backend.service.course.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Course API", description = "강의 관리 (교육자)")
public class CourseController {

    private final CourseService courseService;
    private final AuthUtil authUtil; // Authentication(인증 정보)에서 사용자 정보 빼기 위한 커스텀 클래스

    @GetMapping
    @Operation(summary = "전체 강의 조회", description = "모든 강의 목록을 조회합니다.")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/mine")
    @Operation(summary = "내 강의 목록 조회", description = "현재 로그인한 교육자의 모든 강의를 조회합니다.")
    public ResponseEntity<List<CourseDTO>> getMyCourses(
            @Parameter(hidden = true) Authentication authentication) { // Authentication 은 요청 헤더에서 토큰을 받아오게 됨
        Educator educator = authUtil.getEducator(authentication); // 목적에 맞는 사용자 추출해오기
        return ResponseEntity.ok(courseService.getMyCourses(educator));
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "강의 단건 조회", description = "특정 강의의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "강의를 찾을 수 없음")
    })
    public ResponseEntity<CourseDTO> getCourse(@Parameter(hidden = true) Authentication authentication,
                                               @Parameter(description = "조회할 강좌 ID") @PathVariable("courseId") Long courseId) {
        Educator educator = authUtil.getEducator(authentication);
        return ResponseEntity.ok(courseService.getCourse(educator, courseId));
    }

    @PostMapping
    @Operation(summary = "강의 생성", description = "새로운 강의를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ResponseEntity<Long> createCourse(@Parameter(hidden = true) Authentication authentication,
                                             @RequestBody CourseCreateDTO courseCreateDTO) { // @RequestBody 는 요청 body 에 json 형태로 들어온 데이터를 가져옴
        Educator educator = authUtil.getEducator(authentication);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.createCourse(educator, courseCreateDTO)); // 201 Created HTTP 상태 반환 (어떤 거는 안되어있을 수도 있는데 추후 만들 예정
    }

    @PatchMapping("/{courseId}")
    @Operation(summary = "강의 수정", description = "특정 강의 정보를 수정합니다.")
    public ResponseEntity<Long> updateCourse(@Parameter(hidden = true) Authentication authentication,
                                             @Parameter(description = "수정할 강좌 ID") @PathVariable("courseId") Long courseId,
                                             @RequestBody CourseUpdateDTO req) {
        Educator educator = authUtil.getEducator(authentication);
        return ResponseEntity.ok(courseService.updateCourse(educator, courseId, req));
    }

    @DeleteMapping("/{courseId}")
    @Operation(summary = "강의 삭제", description = "특정 강의를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "강의를 찾을 수 없음")
    })
    public ResponseEntity<Void> deleteCourse(Authentication authentication,
                                             @PathVariable("courseId") Long courseId) {
        Educator educator = authUtil.getEducator(authentication);
        courseService.deleteCourse(educator, courseId);
        return ResponseEntity.noContent().build();
    }

}