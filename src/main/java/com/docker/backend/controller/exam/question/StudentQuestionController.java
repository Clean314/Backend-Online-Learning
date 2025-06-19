package com.docker.backend.controller.exam.question;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.question.StudentQuestionDTO;
import com.docker.backend.service.exam.question.QuestionService;
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
public class StudentQuestionController {
    private final QuestionService questionService;
    private final AuthUtil authUtil;

    private Long getStudentId(Authentication authentication) {
        return authUtil.getStudent(authentication).getId();
    }

    @GetMapping
    public ResponseEntity<List<StudentQuestionDTO>> getQuestions(@RequestParam(name = "courseId") Long courseId,
                                                                 @RequestParam(name = "examId") Long examId,
                                                                 Authentication authentication) {
        return ResponseEntity.ok(questionService.StudentGetAllQuestionsByExamId(getStudentId(authentication), courseId, examId));
    }

}
