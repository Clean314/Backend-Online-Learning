package com.docker.backend.controller.exam;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.*;
import com.docker.backend.dto.exam.question.QuestionCreateUpdateForm;
import com.docker.backend.dto.exam.question.QuestionRequestDTO;
import com.docker.backend.service.exam.ExamService;
import com.docker.backend.service.exam.question.QuestionService;
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
@RequestMapping("/educator/exam")
@PreAuthorize("hasRole('EDUCATOR')")
public class EducatorExamController {

    private final AuthUtil authUtil;
    private final ExamService examService;
    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<EducatorExamDTO>> getExams(Authentication authentication,
                                                          @RequestBody ExamRequestDTO examRequestDTO) {
        return ResponseEntity.ok(examService.getEducatorExamsByCourse(examRequestDTO.getCourseId(), getEducatorId(authentication)));
    }

    @PostMapping
    public ResponseEntity<EducatorExamDTO> createExam(@RequestBody @Valid ExamCreateDTO dto,
                                                      Authentication authentication) {
        EducatorExamDTO created = examService.createExam(dto.getCourseId(), getEducatorId(authentication), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{examId}")
    public ResponseEntity<EducatorExamDTO> getExam(@RequestBody ExamRequestDTO examRequestDTO,
                                                  Authentication authentication) {
        return ResponseEntity.ok(examService.getExamByIdAndCourse(examRequestDTO.getCourseId(), getEducatorId(authentication), examRequestDTO.getExamId()));
    }

    @PutMapping("/{examId}")
    public ResponseEntity<EducatorExamDTO> updateExam(@RequestBody @Valid ExamUpdateDTO dto,
                                                      Authentication authentication) {
        EducatorExamDTO updated = examService.updateExam(dto.getCourseId(), getEducatorId(authentication), dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExam(
                                           Authentication authentication) {
//        examService.deleteExam(examId, getEducatorId(authentication));
        return ResponseEntity.noContent().build();
    }

    private Long getEducatorId(Authentication authentication) {
        return authUtil.getEducator(authentication).getId();
    }

    @GetMapping("/questions")
    public ResponseEntity<List<EducatorQuestionDTO>> getQuestions(Authentication authentication,
                                                                  @RequestBody QuestionRequestDTO request) {
        return ResponseEntity.ok(questionService.EducatorGetAllQuestionByExamId(getEducatorId(authentication), request));
    }

    @PostMapping("/questions")
    public ResponseEntity<EducatorQuestionDTO> createQuestion(Authentication authentication,
                                                              @RequestBody QuestionCreateUpdateForm questionCreateUpdateForm){
        EducatorQuestionDTO created = questionService.EducatorCreateQuestion(getEducatorId(authentication), questionCreateUpdateForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/questions")
    public ResponseEntity<EducatorQuestionDTO> updateQuestion(Authentication authentication,
                                              @RequestBody QuestionCreateUpdateForm questionCreateUpdateForm){
        EducatorQuestionDTO updated = questionService.EducatorUpdateQuestionById(getEducatorId(authentication), questionCreateUpdateForm);
        return ResponseEntity.ok(updated);
    }
}
