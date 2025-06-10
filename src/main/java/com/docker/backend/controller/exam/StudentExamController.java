package com.docker.backend.controller.exam;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.ExamDTO;
import com.docker.backend.service.exam.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/student/exam/{courseId}")
@PreAuthorize("hasRole('STUDENT')")
public class StudentExamController {

    private final AuthUtil authUtil;
    private final ExamService examService;

    @GetMapping
    public ResponseEntity<List<ExamDTO>> getExams(@PathVariable("courseId") Long courseId,
                                                  Authentication authentication) {
        return ResponseEntity.ok(examService.getStudentExamsByCourse(courseId, getStudentId(authentication)));
    }

//    @GetMapping("/{examId}")
//    public ResponseEntity<ExamDTO> getExam(@PathVariable("courseId") Long courseId,
//                                           @PathVariable("examId") Long examId,
//                                           Authentication authentication) {
//        return ResponseEntity.ok(examService.getExamByIdAndCourse(courseId, getStudentId(authentication), examId));
//    }

//    @PostMapping("/{examId}/submit")
//    public ResponseEntity<Void> submitExam(@PathVariable("courseId") Long courseId,
//                                           @PathVariable("examId") Long examId,
//                                           Authentication authentication) {
//        Long studentId = getStudentId(authentication);
//        examService.submitExam(courseId, studentId, examId);
//        return ResponseEntity.noContent().build();
//    }


    private Long getStudentId(Authentication authentication) {
        return authUtil.getStudent(authentication).getId();
    }
}