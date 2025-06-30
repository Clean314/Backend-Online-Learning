package com.docker.backend.controller.exam;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.StudentExamDTO;
import com.docker.backend.service.exam.StudentExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
@RestController
@RequestMapping("/student/exam/{courseId}")
@PreAuthorize("hasRole('STUDENT')")
public class StudentExamController {

    private final AuthUtil authUtil;
    private final StudentExamService studentExamService;

    @GetMapping
    public ResponseEntity<List<StudentExamDTO>> getExams(@PathVariable("courseId") Long courseId,
                                                         Authentication authentication) {
        return ResponseEntity.ok(studentExamService.getExamsByCourse(courseId, getStudentId(authentication)));
    }

    @GetMapping("/{examId}")
    public ResponseEntity<StudentExamDTO> getExam(@PathVariable("courseId") Long courseId,
                                                  @PathVariable("examId") Long examId,
                                                  Authentication authentication) {
        return ResponseEntity.ok(studentExamService.getExamByIdAndCourse(courseId, getStudentId(authentication), examId));
    }

    @PostMapping("/{examId}/start")
    public ResponseEntity<Void> startExam(@PathVariable("courseId") Long courseId,
                                          @PathVariable("examId") Long examId,
                                          Authentication authentication) {
        studentExamService.initializeExamStatus(courseId, examId, getStudentId(authentication));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{examId}/submit")
    public ResponseEntity<String> submitExam(@PathVariable("courseId") Long courseId,
                                             @PathVariable("examId") Long examId,
                                             @RequestBody Map<Long, String> answers,
                                             Authentication authentication) {
        int score = studentExamService.submitExam(courseId, examId, getStudentId(authentication), answers);
        return ResponseEntity.ok("제출 완료, 점수: " + score);
    }

    @PostMapping("/{examId}/save")
    public ResponseEntity<Void> saveExamProgress(@PathVariable("courseId") Long courseId,
                                                 @PathVariable("examId") Long examId,
                                                 @RequestBody Map<Long, String> answers,
                                                 Authentication authentication) {
        studentExamService.saveStudentAnswers(courseId, examId, getStudentId(authentication), answers);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{examId}/score")
    public ResponseEntity<Integer> getExamScore(@PathVariable("courseId") Long courseId,
                                                @PathVariable("examId") Long examId,
                                                Authentication authentication) {
        Long studentId = getStudentId(authentication);
        int score = studentExamService.getStudentExamScore(courseId, examId, studentId);
        return ResponseEntity.ok(score);
    }

    @GetMapping("/{examId}/answers")
    public ResponseEntity<Map<Long, String>> getSavedAnswers(@PathVariable("courseId") Long courseId,
                                                             @PathVariable("examId") Long examId,
                                                             Authentication authentication) {
        Long studentId = getStudentId(authentication);
        Map<Long, String> savedAnswers = studentExamService.getSavedAnswers(courseId, examId, studentId);
        return ResponseEntity.ok(savedAnswers);
    }

    private Long getStudentId(Authentication authentication) {
        return authUtil.getStudent(authentication).getId();
    }
}
