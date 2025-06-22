package com.docker.backend.controller.dashboard;

import com.docker.backend.dto.course.RecentCourseDTO;
import com.docker.backend.domain.user.Educator;
import com.docker.backend.config.CustomUserDetails;
import com.docker.backend.service.dashboard.EducatorDashboardService;
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
 * 강사용 대시보드 API 컨트롤러
 * - 최근 개설 강의 목록 조회
 * - 최근 수정 강의 목록 조회
 * - EDUCATOR 권한만 접근 가능
 */
@Slf4j
@RestController
@RequestMapping("/main_dashboard/educators")
@RequiredArgsConstructor
@PreAuthorize("hasRole('EDUCATOR')")
public class EducatorDashboardController {
    private final EducatorDashboardService educatorDashboardService;

    /**
     * 최근 개설 강의 목록 조회 API
     * - 강사가 개설한 강의 중 최근 4개를 반환
     * - 생성일시 기준 내림차순 정렬
     */
    @GetMapping("/recent-created")
    public ResponseEntity<List<RecentCourseDTO>> getRecentCreatedCourses(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null || !(userDetails.getMember() instanceof Educator)) {
            log.error("인증된 강사 정보를 찾을 수 없습니다.");
            return ResponseEntity.status(401).build();
        }

        Educator educator = (Educator) userDetails.getMember();
        log.info("최근 개설 강의 목록 조회 요청 - 강사 ID: {}", educator.getId());
        try {
            List<RecentCourseDTO> courses = educatorDashboardService.getRecentCreatedCourses(educator);
            log.info("최근 개설 강의 목록 조회 성공 - 조회된 강의 수: {}", courses.size());
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            log.error("최근 개설 강의 목록 조회 실패", e);
            throw e;
        }
    }

    /**
     * 최근 수정 강의 목록 조회 API
     * - 강사가 수정한 강의 중 최근 4개를 반환
     * - 수정일시 기준 내림차순 정렬
     */
    @GetMapping("/recent-updated")
    public ResponseEntity<List<RecentCourseDTO>> getRecentUpdatedCourses(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null || !(userDetails.getMember() instanceof Educator)) {
            log.error("인증된 강사 정보를 찾을 수 없습니다.");
            return ResponseEntity.status(401).build();
        }

        Educator educator = (Educator) userDetails.getMember();
        log.info("최근 수정 강의 목록 조회 요청 - 강사 ID: {}", educator.getId());
        try {
            List<RecentCourseDTO> courses = educatorDashboardService.getRecentUpdatedCourses(educator);
            log.info("최근 수정 강의 목록 조회 성공 - 조회된 강의 수: {}", courses.size());
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            log.error("최근 수정 강의 목록 조회 실패", e);
            throw e;
        }
    }
} 