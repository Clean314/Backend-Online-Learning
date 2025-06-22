package com.docker.backend.controller.dashboard;

import com.docker.backend.dto.course.RecentCourseDTO;
import com.docker.backend.domain.user.Student;
import com.docker.backend.config.CustomUserDetails;
import com.docker.backend.service.dashboard.StudentDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 학생용 대시보드 API 컨트롤러
 * - 최근 수강 강의 목록 조회
 * - 최근 완료 강의 목록 조회
 * - STUDENT 권한만 접근 가능
 */
@Slf4j
@RestController
@RequestMapping("/main_dashboard/students")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentDashboardController {
    private final StudentDashboardService studentDashboardService;

    /**
     * 최근 수강 강의 목록 조회 API
     * - ENROLLED 상태의 강의만 포함
     * - 최근 4개 강의 반환
     * - 생성일시 기준 내림차순 정렬
     */
    @GetMapping("/recent-enrolled")
    public ResponseEntity<List<RecentCourseDTO>> getRecentEnrolledCourses(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null || !(userDetails.getMember() instanceof Student)) {
            log.error("인증된 학생 정보를 찾을 수 없습니다.");
            return ResponseEntity.status(401).build();
        }

        Student student = (Student) userDetails.getMember();
        log.info("최근 수강 강의 목록 조회 요청 - 학생 ID: {}", student.getId());
        try {
            List<RecentCourseDTO> courses = studentDashboardService.getRecentEnrolledCourses(student);
            log.info("최근 수강 강의 목록 조회 성공 - 조회된 강의 수: {}", courses.size());
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            log.error("최근 수강 강의 목록 조회 실패", e);
            throw e;
        }
    }

    /**
     * 최근 완료 강의 목록 조회 API
     * - COMPLETED 상태의 강의만 포함
     * - 최근 4개 강의 반환
     * - 생성일시 기준 내림차순 정렬
     */
    @GetMapping("/recent-completed")
    public ResponseEntity<List<RecentCourseDTO>> getRecentCompletedCourses(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null || !(userDetails.getMember() instanceof Student)) {
            log.error("인증된 학생 정보를 찾을 수 없습니다.");
            return ResponseEntity.status(401).build();
        }

        Student student = (Student) userDetails.getMember();
        log.info("최근 완료 강의 목록 조회 요청 - 학생 ID: {}", student.getId());
        try {
            List<RecentCourseDTO> courses = studentDashboardService.getRecentCompletedCourses(student);
            log.info("최근 완료 강의 목록 조회 성공 - 조회된 강의 수: {}", courses.size());
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            log.error("최근 완료 강의 목록 조회 실패", e);
            throw e;
        }
    }
} 