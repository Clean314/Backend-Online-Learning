package com.docker.backend.controller.exam.question;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.question.EducatorQuestionDTO;
import com.docker.backend.dto.exam.question.QuestionForm;
import com.docker.backend.service.exam.question.EducatorQuestionService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/educator/question")
@Tag(name = "Question API (Educator)", description = "시험 문제 관리 (교육자)")
@PreAuthorize("hasRole('EDUCATOR')")
@SecurityRequirement(name = "bearerAuth")
public class EducatorQuestionController {

    private final EducatorQuestionService educatorQuestionService;
    private final AuthUtil authUtil;

    private Long getEducatorId(Authentication authentication) {
        return authUtil.getEducator(authentication).getId();
    }

    @GetMapping
    @Operation(summary = "시험 문제 목록 조회", description = "특정 시험의 모든 문제를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = EducatorQuestionDTO.class)))
    public ResponseEntity<List<EducatorQuestionDTO>> getQuestions(@Parameter(description = "강좌 ID") @RequestParam(name = "courseId") Long courseId,
                                                                  @Parameter(description = "시험 ID") @RequestParam(name = "examId") Long examId,
                                                                  Authentication authentication) {
        return ResponseEntity.ok(educatorQuestionService.getAllQuestionByExamId(getEducatorId(authentication), courseId, examId));
    }

    @PostMapping
    @Operation(summary = "시험 문제 생성", description = "특정 시험에 새로운 문제를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공",
                    content = @Content(schema = @Schema(implementation = EducatorQuestionDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ResponseEntity<EducatorQuestionDTO> createQuestion(@Parameter(description = "강좌 ID")  @RequestParam(name = "courseId") Long courseId,
                                                              @Parameter(description = "시험 ID") @RequestParam(name = "examId") Long examId,
                                                                @RequestBody QuestionForm questionForm,
                                                                Authentication authentication){
        EducatorQuestionDTO created = educatorQuestionService.createQuestion(getEducatorId(authentication), courseId, examId, questionForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    @Operation(summary = "시험 문제 수정", description = "특정 문제(questionId)를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "수정 성공",
            content = @Content(schema = @Schema(implementation = EducatorQuestionDTO.class)))
    public ResponseEntity<EducatorQuestionDTO> updateQuestion(@Parameter(description = "강좌 ID") @RequestParam(name = "courseId") Long courseId,
                                                              @Parameter(description = "시험 ID") @RequestParam(name = "examId") Long examId,
                                                              @Parameter(description = "문제 ID") @RequestParam(name = "questionId") Long questionId,
                                                              @RequestBody QuestionForm questionForm,
                                                              Authentication authentication){
        EducatorQuestionDTO updated = educatorQuestionService.updateQuestionById(getEducatorId(authentication), courseId, examId, questionId, questionForm);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping
    @Operation(summary = "시험 문제 삭제", description = "특정 문제(questionId)를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "문제를 찾을 수 없음")
    })
    public ResponseEntity<Void> deleteQuestion(@Parameter(description = "강좌 ID") @RequestParam(name = "courseId") Long courseId,
                                               @Parameter(description = "시험 ID") @RequestParam(name = "examId") Long examId,
                                               @Parameter(description = "문제 ID") @RequestParam(name = "questionId") Long questionId,
                                           Authentication authentication) {
        educatorQuestionService.deleteQuestion(getEducatorId(authentication), courseId, examId, questionId);
        return ResponseEntity.noContent().build();
    }
}