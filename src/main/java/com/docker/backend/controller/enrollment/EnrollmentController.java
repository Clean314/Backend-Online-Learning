package com.docker.backend.controller.enrollment;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.course.CourseDTO;
import com.docker.backend.dto.enrollment.EnrollmentCourseDTO;
import com.docker.backend.domain.user.Student;
import com.docker.backend.service.enrollment.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/students/enrollments")
@PreAuthorize("hasRole('STUDENT')")
@Tag(name = "Enrollment API", description = "수강 관리 (학생)")
@SecurityRequirement(name = "bearerAuth")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final AuthUtil authUtil;

    @GetMapping
    @Operation(summary = "전체 수강 가능 강좌 조회", description = "학생이 수강 신청할 수 있는 전체 강좌 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = EnrollmentCourseDTO.class)))
    public ResponseEntity<List<EnrollmentCourseDTO>> getAllCourses(Authentication authentication) {
        Student student = authUtil.getStudent(authentication);
        return ResponseEntity.ok(enrollmentService.getAllEnrollmentCourses(student));
    }

    @GetMapping("/mine")
    @Operation(summary = "내 수강 강좌 조회", description = "현재 로그인한 학생이 수강 중인 모든 강좌를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = EnrollmentCourseDTO.class)))
    public ResponseEntity<List<EnrollmentCourseDTO>> getMyEnrollments(Authentication authentication) {
        Student student = authUtil.getStudent(authentication);
        return ResponseEntity.ok(enrollmentService.getEnrolledCourses(student));
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "내 수강 강좌 단건 조회", description = "특정 강좌의 수강 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = CourseDTO.class))),
            @ApiResponse(responseCode = "404", description = "강좌를 찾을 수 없음")
    })
    public ResponseEntity<CourseDTO> getEnrolledCourseById(
            Authentication authentication,
            @Parameter(description = "조회할 강좌 ID") @PathVariable("courseId") Long courseId) {
        Student student = authUtil.getStudent(authentication);
        return ResponseEntity.ok(enrollmentService.getEnrolledCourseById(student, courseId));
    }

    @PostMapping("/{courseId}")
    @Operation(summary = "강좌 수강 신청", description = "특정 강좌에 수강 신청합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "수강 신청 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "409", description = "이미 수강 중인 강좌")
    })
    public ResponseEntity<Void> enroll(
            Authentication authentication,
            @Parameter(description = "수강 신청할 강좌 ID") @PathVariable("courseId") Long courseId) {
        Student student = authUtil.getStudent(authentication);
        enrollmentService.enroll(student, courseId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{courseId}")
    @Operation(summary = "강좌 수강 취소", description = "특정 강좌의 수강을 취소합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "수강 취소 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "강좌를 찾을 수 없음")
    })
    public ResponseEntity<Void> cancel(
            Authentication authentication,
            @Parameter(description = "수강 취소할 강좌 ID") @PathVariable("courseId") Long courseId) {
        Student student = authUtil.getStudent(authentication);
        enrollmentService.cancelEnroll(student, courseId);
        return ResponseEntity.noContent().build();
    }
}