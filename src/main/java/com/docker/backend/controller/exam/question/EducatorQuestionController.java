package com.docker.backend.controller.exam.question;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.question.EducatorQuestionDTO;
import com.docker.backend.dto.exam.question.QuestionForm;
import com.docker.backend.service.exam.question.EducatorQuestionService;
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
@PreAuthorize("hasRole('EDUCATOR')")
public class EducatorQuestionController {

    private final EducatorQuestionService educatorQuestionService;
    private final AuthUtil authUtil;

    private Long getEducatorId(Authentication authentication) {
        return authUtil.getEducator(authentication).getId();
    }

    @GetMapping
    public ResponseEntity<List<EducatorQuestionDTO>> getQuestions(@RequestParam(name = "courseId") Long courseId,
                                                                  @RequestParam(name = "examId") Long examId,
                                                                  Authentication authentication) {
        return ResponseEntity.ok(educatorQuestionService.getAllQuestionByExamId(getEducatorId(authentication), courseId, examId));
    }

    @PostMapping
    public ResponseEntity<EducatorQuestionDTO> createQuestion(@RequestParam(name = "courseId") Long courseId,
                                                                @RequestParam(name = "examId") Long examId,
                                                                @RequestBody QuestionForm questionForm,
                                                                Authentication authentication){
        EducatorQuestionDTO created = educatorQuestionService.createQuestion(getEducatorId(authentication), courseId, examId, questionForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    public ResponseEntity<EducatorQuestionDTO> updateQuestion(@RequestParam(name = "courseId") Long courseId,
                                                              @RequestParam(name = "examId") Long examId,
                                                              @RequestParam(name = "questionId") Long questionId,
                                                              @RequestBody QuestionForm questionForm,
                                                              Authentication authentication){
        EducatorQuestionDTO updated = educatorQuestionService.updateQuestionById(getEducatorId(authentication), courseId, examId, questionId, questionForm);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteQuestion(@RequestParam(name = "courseId") Long courseId,
                                               @RequestParam(name = "examId") Long examId,
                                               @RequestParam(name = "questionId") Long questionId,
                                           Authentication authentication) {
        educatorQuestionService.deleteQuestion(getEducatorId(authentication), courseId, examId, questionId);
        return ResponseEntity.noContent().build();
    }
}