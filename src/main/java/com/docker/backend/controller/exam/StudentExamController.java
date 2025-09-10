package com.docker.backend.controller.exam;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.StudentExamDTO;
import com.docker.backend.service.exam.StudentExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students/courses/{courseId}/exams")
@PreAuthorize("hasRole('STUDENT')")
@Tag(name = "Student Exam API", description = "시험 관리 (학생)")
@SecurityRequirement(name = "bearerAuth")
public class StudentExamController {

    private final AuthUtil authUtil;
    private final StudentExamService studentExamService;

    private Long getStudentId(Authentication authentication) {
        return authUtil.getStudent(authentication).getId();
    }

    @GetMapping
    @Operation(summary = "시험 목록 조회", description = "특정 강좌(courseId)의 모든 시험을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = StudentExamDTO.class)))
    public ResponseEntity<List<StudentExamDTO>> getExams(
            @Parameter(description = "강좌 ID") @PathVariable Long courseId,
            Authentication authentication) {
        return ResponseEntity.ok(studentExamService.getExamsByCourse(courseId, getStudentId(authentication)));
    }

    @GetMapping("/{examId}")
    @Operation(summary = "시험 상세 조회", description = "특정 시험(examId)의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = StudentExamDTO.class))),
            @ApiResponse(responseCode = "404", description = "시험을 찾을 수 없음")
    })
    public ResponseEntity<StudentExamDTO> getExam(
            @Parameter(description = "강좌 ID") @PathVariable("courseId") Long courseId,
            @Parameter(description = "시험 ID") @PathVariable("examId") Long examId,
            Authentication authentication) {
        return ResponseEntity.ok(studentExamService.getExamByIdAndCourse(courseId, getStudentId(authentication), examId));
    }

    @PostMapping("/{examId}/start")
    @Operation(summary = "시험 시작", description = "특정 시험을 시작하여 상태를 초기화합니다.")
    @ApiResponse(responseCode = "200", description = "시험 시작 성공")
    public ResponseEntity<Void> startExam(
            @Parameter(description = "강좌 ID") @PathVariable("courseId") Long courseId,
            @Parameter(description = "시험 ID") @PathVariable("examId") Long examId,
            Authentication authentication) {
        studentExamService.initializeExamStatus(courseId, examId, getStudentId(authentication));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{examId}/submit")
    @Operation(summary = "시험 제출", description = "학생이 시험 답안을 제출합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "제출 성공 (점수 반환)"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ResponseEntity<String> submitExam(
            @Parameter(description = "강좌 ID") @PathVariable("courseId") Long courseId,
            @Parameter(description = "시험 ID") @PathVariable("examId") Long examId,
            @RequestBody Map<Long, String> answers,
            Authentication authentication) {
        int score = studentExamService.submitExam(courseId, examId, getStudentId(authentication), answers);
        return ResponseEntity.ok("제출 완료, 점수: " + score);
    }

    @PostMapping("/{examId}/save")
    @Operation(summary = "시험 진행 상황 저장", description = "시험 도중 답안을 임시 저장합니다.")
    @ApiResponse(responseCode = "200", description = "저장 성공")
    public ResponseEntity<Void> saveExamProgress(
            @Parameter(description = "강좌 ID") @PathVariable("courseId") Long courseId,
            @Parameter(description = "시험 ID") @PathVariable("examId") Long examId,
            @RequestBody Map<Long, String> answers,
            Authentication authentication) {
        studentExamService.saveStudentAnswers(courseId, examId, getStudentId(authentication), answers);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{examId}/score")
    @Operation(summary = "시험 점수 조회", description = "학생의 특정 시험 점수를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "404", description = "시험을 찾을 수 없음")
    })
    public ResponseEntity<Integer> getExamScore(
            @Parameter(description = "강좌 ID") @PathVariable("courseId") Long courseId,
            @Parameter(description = "시험 ID") @PathVariable("examId") Long examId,
            Authentication authentication) {
        Long studentId = getStudentId(authentication);
        int score = studentExamService.getStudentExamScore(courseId, examId, studentId);
        return ResponseEntity.ok(score);
    }

    @GetMapping("/{examId}/answers")
    @Operation(summary = "임시 저장 답안 조회", description = "학생이 시험 도중 저장한 답안을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "답안을 찾을 수 없음")
    })
    public ResponseEntity<Map<Long, String>> getSavedAnswers(
            @Parameter(description = "강좌 ID") @PathVariable("courseId") Long courseId,
            @Parameter(description = "시험 ID") @PathVariable("examId") Long examId,
            Authentication authentication) {
        Long studentId = getStudentId(authentication);
        Map<Long, String> savedAnswers = studentExamService.getSavedAnswers(courseId, examId, studentId);
        return ResponseEntity.ok(savedAnswers);
    }
}

