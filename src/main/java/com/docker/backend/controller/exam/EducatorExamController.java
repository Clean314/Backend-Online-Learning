package com.docker.backend.controller.exam;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.*;
import com.docker.backend.service.enrollment.EnrollmentService;
import com.docker.backend.service.exam.EducatorExamService;
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
    private final EnrollmentService enrollmentService;
    private final EducatorExamService educatorExamService;
    private final AuthUtil authUtil;

    private Long getEducatorId(Authentication authentication) {
        Long id = authUtil.getEducator(authentication).getId();
        return id;
    }

    @GetMapping
    public ResponseEntity<List<EducatorExamDTO>> getExams(@RequestParam("courseId") Long courseId,
                                                          Authentication authentication) {
        return ResponseEntity.ok(educatorExamService.getExamsByCourse(courseId, getEducatorId(authentication)));
    }

    @PostMapping
    public ResponseEntity<EducatorExamDTO> createExam(@RequestParam("courseId") Long courseId,
                                                      @RequestBody @Valid ExamCreateDTO dto,
                                                      Authentication authentication) {
        Long educatorId = getEducatorId(authentication);
        EducatorExamDTO created = educatorExamService.createExam(educatorId, courseId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{examId}")
    public ResponseEntity<EducatorExamDTO> getExam(@PathVariable Long examId,
                                                   @RequestParam("courseId") Long courseId,
                                                   Authentication authentication) {
        return ResponseEntity.ok(educatorExamService.getExamByIdAndCourse(getEducatorId(authentication), courseId, examId));
    }

    @PutMapping("/{examId}")
    public ResponseEntity<EducatorExamDTO> updateExam(@PathVariable Long examId,
                                                      @RequestParam("courseId") Long courseId,
                                                      @RequestBody @Valid ExamUpdateDTO dto,
                                                      Authentication authentication) {
        Long educatorId = getEducatorId(authentication);
        EducatorExamDTO updated = educatorExamService.updateExam(educatorId, courseId, examId, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long examId,
                                           @RequestParam("courseId") Long courseId,
                                           Authentication authentication) {
        educatorExamService.deleteExam(courseId, examId, getEducatorId(authentication));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{examId}/student-submits")
    public ResponseEntity<List<StudentExamSubmissionDTO>> getStudentSubmissions(@PathVariable("examId") Long examId,
                                                                                @RequestParam("courseId") Long courseId,
                                                                                Authentication authentication) {
        Long educatorId = getEducatorId(authentication);
        List<StudentExamSubmissionDTO> submissions = educatorExamService.getExamSubmissions(courseId, examId, educatorId);
        return ResponseEntity.ok(submissions);
    }


    @PatchMapping("/{examId}/student-submits/{studentId}/answers/{questionId}")
    public ResponseEntity<Void> updateAnswerEvaluation(@PathVariable("examId") Long examId,
                                                       @PathVariable("studentId") Long studentId,
                                                       @PathVariable("questionId") Long questionId,
                                                       @RequestParam("courseId") Long courseId,
                                                       @RequestBody @Valid AnswerEvaluationUpdateDTO dto,
                                                       Authentication authentication) {
        Long educatorId = getEducatorId(authentication);
        educatorExamService.updateAnswerEvaluation(courseId, examId, studentId, questionId, educatorId, dto);
        return ResponseEntity.ok().build();
    }

}
