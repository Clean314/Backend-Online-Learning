package com.docker.backend.controller.exam;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.*;
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
@RequestMapping("/educator/exam/{courseId}")
@PreAuthorize("hasRole('EDUCATOR')")
public class EducatorExamController {

    private final AuthUtil authUtil;
    private final ExamService examService;
    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<EducatorExamDTO>> getExams(@PathVariable("courseId") Long courseId,
                                                          Authentication authentication) {
        return ResponseEntity.ok(examService.getEducatorExamsByCourse(courseId, getEducatorId(authentication)));
    }

    @PostMapping
    public ResponseEntity<EducatorExamDTO> createExam(@PathVariable("courseId") Long courseId,
                                                      @RequestBody @Valid ExamCreateDTO dto,
                                                      Authentication authentication) {
        EducatorExamDTO created = examService.createExam(courseId, getEducatorId(authentication), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{examId}")
    public ResponseEntity<EducatorExamDTO> getExam(@PathVariable("courseId") Long courseId,
                                                  @PathVariable("examId") Long examId,
                                                  Authentication authentication) {
        return ResponseEntity.ok(examService.getExamByIdAndCourse(courseId, getEducatorId(authentication), examId));
    }

    @PutMapping("/{examId}")
    public ResponseEntity<EducatorExamDTO> updateExam(@PathVariable("courseId") Long courseId,
                                                      @PathVariable("examId") Long examId,
                                                      @RequestBody @Valid ExamUpdateDTO dto,
                                                      Authentication authentication) {
        EducatorExamDTO updated = examService.updateExam(examId, getEducatorId(authentication), dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExam(@PathVariable("courseId") Long courseId,
                                           @PathVariable("examId") Long examId,
                                           Authentication authentication) {
        examService.deleteExam(examId, getEducatorId(authentication));
        return ResponseEntity.noContent().build();
    }

    private Long getEducatorId(Authentication authentication) {
        return authUtil.getEducator(authentication).getId();
    }

    @GetMapping("/{examId}")
    public ResponseEntity<List<EducatorQuestionDTO>> getQuestions(Authentication authentication,
                                                                  @RequestBody ExamRequestDTO request) {
        return ResponseEntity.ok(questionService);
    }
}
