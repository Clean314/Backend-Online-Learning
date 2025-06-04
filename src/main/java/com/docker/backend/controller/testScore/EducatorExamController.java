package com.docker.backend.controller.testScore;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.entity.Exam;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.service.testScore.ExamService;
import lombok.RequiredArgsConstructor;
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

    private final AuthUtil authUtil;
    private final ExamService examService;

    // 강의에 해당하는 내가 만든 모든 시험 조회
    @GetMapping("/course-id/{courseId}")
    public ResponseEntity<List<Exam>> getMyExamByCourse(Authentication authentication, @PathVariable("courseId") Long courseId) {
        Educator educator = authUtil.getEducator(authentication);
        return ResponseEntity.ok(examService.getAllExamByCourse(courseId, educator.getId()));
    }

    // 특정 시험 조회
    @GetMapping("/exam-id/{examId}/{courseId}")
    public ResponseEntity<Exam> getExamByIdAndCourse(Authentication authentication, @PathVariable("examId") Long examId, @PathVariable("courseId") Long courseId) {
        Educator educator = authUtil.getEducator(authentication);
        Exam exam = examService.getExamByCourseAndId(educator.getId(), courseId, examId);
        return ResponseEntity.ok(exam);
    }

    // 새로운 시험 생성
//    @PostMapping
//    public ResponseEntity<Exam> createExam(Authentication authentication, @RequestBody Exam exam) {
//        Educator educator = authUtil.getEducator(authentication);
//        Exam createdExam = examService.createTestScore(educator, exam);
//        return ResponseEntity.status(201).body(createdExam);
//    }



}