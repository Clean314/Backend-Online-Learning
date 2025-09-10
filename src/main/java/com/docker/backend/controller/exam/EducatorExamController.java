package com.docker.backend.controller.exam;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.course.CourseDTO;
import com.docker.backend.dto.exam.*;
import com.docker.backend.service.enrollment.EnrollmentService;
import com.docker.backend.service.exam.EducatorExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/educators/courses/{courseId}/exams")
@PreAuthorize("hasRole('EDUCATOR')")
@Tag(name = "Educator Exam API", description = "시험 및 답안 관리 (교육자)")
@SecurityRequirement(name = "bearerAuth")
public class EducatorExamController {
    private final EnrollmentService enrollmentService;
    private final EducatorExamService educatorExamService;
    private final AuthUtil authUtil;

    private Long getEducatorId(Authentication authentication) {
        Long id = authUtil.getEducator(authentication).getId();
        return id;
    }

    @Operation(summary = "시험 목록 조회", description = "특정 강의(courseId)의 시험들을 조회합니다.")
    @ApiResponse(
            responseCode = "200",
            description = "시험 목록 조회 성공",
            content = @Content(schema = @Schema(implementation = EducatorExamDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<EducatorExamDTO>> getExams(@PathVariable Long courseId,
                                                          Authentication authentication) {
        return ResponseEntity.ok(educatorExamService.getExamsByCourse(courseId, getEducatorId(authentication)));
    }

    @Operation(summary = "시험 생성", description = "특정 강의(courseId)에 새로운 시험을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "시험 생성 성공",
                    content = @Content(schema = @Schema(implementation = EducatorExamDTO.class))),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 입력값")
    })
    @PostMapping
    public ResponseEntity<EducatorExamDTO> createExam(@PathVariable Long courseId,
                                                      @RequestBody @Valid ExamCreateDTO dto,
                                                      Authentication authentication) {
        Long educatorId = getEducatorId(authentication);
        EducatorExamDTO created = educatorExamService.createExam(educatorId, courseId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "시험 상세 조회", description = "특정 시험(examId)의 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "시험 상세 조회 성공")
    @GetMapping("/{examId}")
    public ResponseEntity<EducatorExamDTO> getExam(@PathVariable("courseId") Long courseId,
                                                   @PathVariable Long examId,
                                                   Authentication authentication) {
        return ResponseEntity.ok(educatorExamService.getExamByIdAndCourse(getEducatorId(authentication), courseId, examId));
    }

    @Operation(summary = "시험 수정", description = "특정 시험(examId)의 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "시험 수정 성공")
    @PutMapping("/{examId}")
    public ResponseEntity<EducatorExamDTO> updateExam(@PathVariable("courseId") Long courseId,
                                                      @PathVariable("examId")  Long examId,
                                                      @RequestBody @Valid ExamUpdateDTO dto,
                                                      Authentication authentication) {
        Long educatorId = getEducatorId(authentication);
        EducatorExamDTO updated = educatorExamService.updateExam(educatorId, courseId, examId, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "시험 삭제", description = "특정 시험(examId)을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "시험 삭제 성공")
    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExam(@PathVariable("courseId") Long courseId,
                                           @PathVariable("examId") Long examId,
                                           Authentication authentication) {
        educatorExamService.deleteExam(courseId, examId, getEducatorId(authentication));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "시험 응시 결과 조회", description = "특정 시험의 학생 제출 결과를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "학생 응시 결과 조회 성공")
    @GetMapping("/{examId}/submissions")
    public ResponseEntity<List<StudentExamSubmissionDTO>> getStudentSubmissions(@PathVariable("courseId") Long courseId,
                                                                                @PathVariable("examId") Long examId,
                                                                                Authentication authentication) {
        Long educatorId = getEducatorId(authentication);
        List<StudentExamSubmissionDTO> submissions = educatorExamService.getExamSubmissions(courseId, examId, educatorId);
        return ResponseEntity.ok(submissions);
    }

    @Operation(summary = "답안 평가 수정", description = "특정 학생의 특정 문제에 대한 답안 평가를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "답안 평가 수정 성공")
    @PatchMapping("/{examId}/submissions/{studentId}/answers/{questionId}")
    public ResponseEntity<Void> updateAnswerEvaluation(@PathVariable Long courseId,
                                                       @PathVariable Long examId,
                                                       @PathVariable Long studentId,
                                                       @PathVariable Long questionId,
                                                       @RequestBody @Valid AnswerEvaluationUpdateDTO dto,
                                                       Authentication authentication) {
        Long educatorId = getEducatorId(authentication);
        educatorExamService.updateAnswerEvaluation(courseId, examId, studentId, questionId, educatorId, dto);
        return ResponseEntity.ok().build();
    }

}
