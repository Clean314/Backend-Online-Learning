package com.docker.backend.controller.testScore;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.ExamCreateDTO;
import com.docker.backend.dto.exam.ExamDTO;
import com.docker.backend.dto.exam.ExamUpdateDTO;
import com.docker.backend.entity.user.Educator;
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
@RequestMapping("/educator/courses/{courseId}/exams")
@PreAuthorize("hasRole('EDUCATOR')")
public class EducatorExamController {

    private final AuthUtil authUtil;
    private final ExamService examService;

    @GetMapping
    public ResponseEntity<List<ExamDTO>> getExams(@PathVariable Long courseId,
                                                  Authentication authentication) {
        Long educatorId = getEducatorId(authentication);
        return ResponseEntity.ok(examService.getExamsByCourse(courseId, educatorId));
    }

    @PostMapping
    public ResponseEntity<ExamDTO> createExam(@PathVariable Long courseId,
                                              @RequestBody @Valid ExamCreateDTO dto,
                                              Authentication authentication) {
        Long educatorId = getEducatorId(authentication);
        ExamDTO created = examService.createExam(courseId, educatorId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{examId}")
    public ResponseEntity<ExamDTO> getExam(@PathVariable Long courseId,
                                           @PathVariable Long examId,
                                           Authentication authentication) {
        Long educatorId = getEducatorId(authentication);
        return ResponseEntity.ok(examService.getExamByIdAndCourse(courseId, educatorId, examId));
    }

    @PutMapping("/{examId}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable Long courseId,
                                              @PathVariable Long examId,
                                              @RequestBody @Valid ExamUpdateDTO dto,
                                              Authentication authentication) {
        Long educatorId = getEducatorId(authentication);
        ExamDTO updated = examService.updateExam(examId, educatorId, dto);
        return ResponseEntity.ok(updated);
    }

    private Long getEducatorId(Authentication authentication) {
        return authUtil.getEducator(authentication).getId();
    }
}
