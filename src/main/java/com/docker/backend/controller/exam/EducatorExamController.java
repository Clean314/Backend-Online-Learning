package com.docker.backend.controller.exam;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.*;
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
@RequestMapping("/educator/exam")
@PreAuthorize("hasRole('EDUCATOR')")
public class EducatorExamController {

    private final ExamService examService;
    private final AuthUtil authUtil;

    private Long getEducatorId(Authentication authentication) {
        return authUtil.getEducator(authentication).getId();
    }

    // 시험 목록 조회
    @GetMapping
    public ResponseEntity<List<EducatorExamDTO>> getExams(@RequestParam("courseId") Long courseId,
                                                          Authentication authentication) {
        return ResponseEntity.ok(examService.getEducatorExamsByCourse(courseId, getEducatorId(authentication)));
    }

    // 시험 생성
    @PostMapping
    public ResponseEntity<EducatorExamDTO> createExam(@RequestParam("courseId") Long courseId,
                                                      @RequestBody @Valid ExamCreateDTO dto,
                                                      Authentication authentication) {
        EducatorExamDTO created = examService.createExam(courseId, getEducatorId(authentication), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 특정 시험 조회
    @GetMapping("/{examId}")
    public ResponseEntity<EducatorExamDTO> getExam(@PathVariable Long examId,
                                                   @RequestParam("courseId") Long courseId,
                                                   Authentication authentication) {
        return ResponseEntity.ok(examService.getEducatorExamByIdAndCourse(getEducatorId(authentication), courseId, examId));
    }

    // 시험 수정
    @PutMapping("/{examId}")
    public ResponseEntity<EducatorExamDTO> updateExam(@PathVariable Long examId,
                                                      @RequestParam("courseId") Long courseId,
                                                      @RequestBody @Valid ExamUpdateDTO dto,
                                                      Authentication authentication) {
        EducatorExamDTO updated = examService.updateExam(courseId, examId, getEducatorId(authentication), dto);
        return ResponseEntity.ok(updated);
    }

    // 시험 삭제
    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long examId,
                                           @RequestParam("courseId") Long courseId,
                                           Authentication authentication) {
        examService.deleteExam(courseId, examId, getEducatorId(authentication));
        return ResponseEntity.noContent().build();
    }
}
