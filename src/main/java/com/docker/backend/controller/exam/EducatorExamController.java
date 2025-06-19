package com.docker.backend.controller.exam;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.*;
import com.docker.backend.service.enrollment.EnrollmentService;
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

    private final EnrollmentService enrollmentService;
    private final ExamService examService;
    private final QuestionService questionService;

//     @GetMapping
//     public ResponseEntity<List<EducatorExamDTO>> getExams(Authentication authentication,
//                                                           @RequestBody ExamRequestDTO examRequestDTO) {
//         return ResponseEntity.ok(examService.getEducatorExamsByCourse(examRequestDTO.getCourseId(), getEducatorId(authentication)));
//     }

//     @PostMapping
//     public ResponseEntity<EducatorExamDTO> createExam(@RequestBody @Valid ExamCreateDTO dto,
//                                                       Authentication authentication) {
//         EducatorExamDTO created = examService.createExam(dto.getCourseId(), getEducatorId(authentication), dto);
//         return ResponseEntity.status(HttpStatus.CREATED).body(created);
//     }

//     @GetMapping("/{examId}")
//     public ResponseEntity<EducatorExamDTO> getExam(@RequestBody ExamRequestDTO examRequestDTO,
//                                                   Authentication authentication) {
//         return ResponseEntity.ok(examService.getExamByIdAndCourse(examRequestDTO.getCourseId(), getEducatorId(authentication), examRequestDTO.getExamId()));
//     }

//     @PutMapping("/{examId}")
//     public ResponseEntity<EducatorExamDTO> updateExam(@RequestBody @Valid ExamUpdateDTO dto,
//                                                       Authentication authentication) {
//         EducatorExamDTO updated = examService.updateExam(dto.getCourseId(), getEducatorId(authentication), dto);
//         return ResponseEntity.ok(updated);
//     }

//     @DeleteMapping("/{examId}")
//     public ResponseEntity<Void> deleteExam(
//                                            Authentication authentication) {
// //        examService.deleteExam(examId, getEducatorId(authentication));
//         return ResponseEntity.noContent().build();
//     }

    private Long getEducatorId(Authentication authentication) {
        return authUtil.getEducator(authentication).getId();
    }

    @GetMapping
    public ResponseEntity<List<EducatorExamDTO>> getExams(@RequestParam("courseId") Long courseId,
                                                          Authentication authentication) {
        return ResponseEntity.ok(examService.getEducatorExamsByCourse(courseId, getEducatorId(authentication)));
    }

    @PostMapping
    public ResponseEntity<EducatorExamDTO> createExam(@RequestParam("courseId") Long courseId,
                                                      @RequestBody @Valid ExamCreateDTO dto,
                                                      Authentication authentication) {
        EducatorExamDTO created = examService.createExam(courseId, getEducatorId(authentication), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{examId}")
    public ResponseEntity<EducatorExamDTO> getExam(@PathVariable Long examId,
                                                   @RequestParam("courseId") Long courseId,
                                                   Authentication authentication) {
        return ResponseEntity.ok(examService.getEducatorExamByIdAndCourse(getEducatorId(authentication), courseId, examId));
    }

    @PutMapping("/{examId}")
    public ResponseEntity<EducatorExamDTO> updateExam(@PathVariable Long examId,
                                                      @RequestParam("courseId") Long courseId,
                                                      @RequestBody @Valid ExamUpdateDTO dto,
                                                      Authentication authentication) {
        EducatorExamDTO updated = examService.updateExam(courseId, examId, getEducatorId(authentication), dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long examId,
                                           @RequestParam("courseId") Long courseId,
                                           Authentication authentication) {
        examService.deleteExam(courseId, examId, getEducatorId(authentication));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{examId}/student-submits")
    public ResponseEntity<List<StudentExamSubmissionDTO>> getStudentSubmissions(@PathVariable("examId") Long examId,
                                                                                @RequestParam("courseId") Long courseId,
                                                                                Authentication authentication) {
        Long educatorId = getEducatorId(authentication);
        List<StudentExamSubmissionDTO> submissions = examService.getStudentSubmissions(courseId, examId, educatorId);
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
        examService.updateAnswerEvaluation(courseId, examId, studentId, questionId, educatorId, dto);
        return ResponseEntity.ok().build();
    }

}
