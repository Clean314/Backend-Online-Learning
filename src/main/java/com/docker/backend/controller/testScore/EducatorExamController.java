package com.docker.backend.controller.testScore;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.ExamCreateDTO;
import com.docker.backend.dto.exam.ExamDTO;
import com.docker.backend.dto.exam.ExamUpdateDTO;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.service.exam.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/educator/exams")
@PreAuthorize("hasRole('EDUCATOR')")
public class EducatorExamController {

    private final AuthUtil authUtil;
    private final ExamService examService;

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<List<ExamDTO>> getExams(@PathVariable(value = "courseId") Long courseId, Authentication authentication) {
        Educator educator = authUtil.getEducator(authentication);
        return ResponseEntity.ok(examService.getExamsByCourse(courseId, educator.getId()));
    }

    @PostMapping("/courses/{courseId}")
    public ResponseEntity<ExamDTO> createExam(@PathVariable(value = "courseId") Long courseId,
                                           @RequestBody ExamCreateDTO examCreateDTO,
                                           Authentication authentication) {
        Educator educator = authUtil.getEducator(authentication);
        return ResponseEntity.status(201).body(examService.createExam(courseId, educator, examCreateDTO));
    }

    @GetMapping("/courses/{courseId}/exam/{examId}")
    public ResponseEntity<ExamDTO> getExam(@PathVariable(value = "examId") Long examId,
                                           @PathVariable(value = "courseId") Long courseId,
                                           Authentication authentication) {
        Educator educator = authUtil.getEducator(authentication);
        ExamDTO exam = examService.getExamByIdAndCourse(courseId, educator.getId(), examId);
        return ResponseEntity.ok(exam);
    }

    @PutMapping("/courses/{courseId}/exam/{examId}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable(value = "examId") Long examId,
                                              @PathVariable(value = "courseId") Long courseId,
                                              @RequestBody ExamUpdateDTO examUpdateDTO,
                                              Authentication authentication) {
        Educator educator = authUtil.getEducator(authentication);
        return ResponseEntity.ok(examService.updateExam(examId, educator.getId(), examUpdateDTO));
    }
}
