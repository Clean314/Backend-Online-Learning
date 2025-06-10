package com.docker.backend.controller.exam;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.ExamCreateDTO;
import com.docker.backend.dto.exam.ExamDTO;
import com.docker.backend.dto.exam.ExamUpdateDTO;
import com.docker.backend.service.exam.ExamService;
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

    @GetMapping
    public ResponseEntity<List<ExamDTO>> getExams(@PathVariable("courseId") Long courseId,
                                                  Authentication authentication) {
        return ResponseEntity.ok(examService.getEducatorExamsByCourse(courseId, getEducatorId(authentication)));
    }

    @PostMapping
    public ResponseEntity<ExamDTO> createExam(@PathVariable("courseId") Long courseId,
                                              @RequestBody @Valid ExamCreateDTO dto,
                                              Authentication authentication) {
        ExamDTO created = examService.createExam(courseId, getEducatorId(authentication), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{examId}")
    public ResponseEntity<ExamDTO> getExam(@PathVariable("courseId") Long courseId,
                                           @PathVariable("examId") Long examId,
                                           Authentication authentication) {
        return ResponseEntity.ok(examService.getExamByIdAndCourse(courseId, getEducatorId(authentication), examId));
    }

    @PutMapping("/{examId}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable("courseId") Long courseId,
                                              @PathVariable("examId") Long examId,
                                              @RequestBody @Valid ExamUpdateDTO dto,
                                              Authentication authentication) {
        ExamDTO updated = examService.updateExam(examId, getEducatorId(authentication), dto);
        return ResponseEntity.ok(updated);
    }

    private Long getEducatorId(Authentication authentication) {
        return authUtil.getEducator(authentication).getId();
    }
}
