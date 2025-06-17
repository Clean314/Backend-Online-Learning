package com.docker.backend.service.dashboard;

import com.docker.backend.dto.course.RecentCourseDTO;
import com.docker.backend.entity.course.Course;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.repository.course.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 강사 대시보드 관련 서비스
 * - 최근 개설 강의 목록 조회
 * - 최근 수정 강의 목록 조회
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EducatorDashboardService {
    private final CourseRepository courseRepository;

    /**
     * 최근 개설 강의 목록 조회
     * - 최근 4개 강의 반환
     * - 생성일시 기준 내림차순 정렬
     */
    public List<RecentCourseDTO> getRecentCreatedCourses(Educator educator) {
        log.info("최근 개설 강의 목록 조회 - 강사 ID: {}", educator.getId());
        return courseRepository.findTop4ByEducatorOrderByCreatedAtDesc(educator)
                .stream()
                .map(this::convertToRecentCourseDTO)
                .collect(Collectors.toList());
    }

    /**
     * 최근 수정 강의 목록 조회
     * - 최근 4개 강의 반환
     * - 수정일시 기준 내림차순 정렬
     */
    public List<RecentCourseDTO> getRecentUpdatedCourses(Educator educator) {
        log.info("최근 수정 강의 목록 조회 - 강사 ID: {}", educator.getId());
        return courseRepository.findTop4ByEducatorOrderByUpdatedAtDesc(educator)
                .stream()
                .map(this::convertToRecentCourseDTO)
                .collect(Collectors.toList());
    }

    private RecentCourseDTO convertToRecentCourseDTO(Course course) {
        return RecentCourseDTO.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .educatorName(course.getEducator().getName())
                .category(course.getCategory())
                .createdAt(course.getCreatedAt())
                .build();
    }
} 