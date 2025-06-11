package com.docker.backend.controller.exam;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.exam.EducatorExamDTO;
import com.docker.backend.dto.exam.StudentExamDTO;
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
    public ResponseEntity<List<StudentExamDTO>> getExams(@PathVariable("courseId") Long courseId,
                                                         Authentication authentication) {
        return ResponseEntity.ok(examService.getStudentExamsByCourse(courseId, getStudentId(authentication)));
    }

    @GetMapping("/{examId}")
    public ResponseEntity<StudentExamDTO> getExam(@PathVariable("courseId") Long courseId,
                                           @PathVariable("examId") Long examId,
                                           Authentication authentication) {
        return ResponseEntity.ok(examService.getStudentExamByIdAndCourse(courseId, getStudentId(authentication), examId));
    }


    private Long getStudentId(Authentication authentication) {
        return authUtil.getStudent(authentication).getId();
    }
}