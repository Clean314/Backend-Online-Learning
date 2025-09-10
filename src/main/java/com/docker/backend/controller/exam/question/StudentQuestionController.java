package com.docker.backend.controller.exam.question;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.question.StudentQuestionDTO;
import com.docker.backend.service.exam.question.StudentQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student/question")
@PreAuthorize("hasRole('STUDENT')")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Question API (Student)", description = "시험 문제 조회 (학생)")
public class StudentQuestionController {
    private final StudentQuestionService studentQuestionService;
    private final AuthUtil authUtil;

    private Long getStudentId(Authentication authentication) {
        return authUtil.getStudent(authentication).getId();
    }

    @GetMapping
    @Operation(summary = "시험 문제 목록 조회", description = "특정 시험의 모든 문제를 조회합니다. (학생용)")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = StudentQuestionDTO.class)))
    public ResponseEntity<List<StudentQuestionDTO>> getQuestions(@RequestParam(name = "courseId") Long courseId,
                                                                 @RequestParam(name = "examId") Long examId,
                                                                 Authentication authentication) {
        return ResponseEntity.ok(studentQuestionService.getAllQuestionsByExamId(getStudentId(authentication), courseId, examId));
    }

}
