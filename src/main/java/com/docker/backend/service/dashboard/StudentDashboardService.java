package com.docker.backend.service.dashboard;

import com.docker.backend.dto.course.RecentCourseDTO;
import com.docker.backend.entity.course.Course;
import com.docker.backend.entity.user.Student;
import com.docker.backend.repository.course.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 학생 대시보드 관련 서비스
 * - 최근 수강 강의 목록 조회
 * - 최근 완료 강의 목록 조회
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentDashboardService {
    private final CourseRepository courseRepository;

    /**
     * 최근 수강 강의 목록 조회
     * - ENROLLED 상태의 강의만 포함
     * - 최근 4개 강의 반환
     * - 생성일시 기준 내림차순 정렬
     */
    public List<RecentCourseDTO> getRecentEnrolledCourses(Student student) {
        log.info("최근 수강 강의 목록 조회 - 학생 ID: {}", student.getId());
        return courseRepository.findRecentEnrolledCoursesByStudent(student)
                .stream()
                .map(this::convertToRecentCourseDTO)
                .collect(Collectors.toList());
    }

    /**
     * 최근 완료 강의 목록 조회
     * - COMPLETED 상태의 강의만 포함
     * - 최근 4개 강의 반환
     * - 생성일시 기준 내림차순 정렬
     */
    public List<RecentCourseDTO> getRecentCompletedCourses(Student student) {
        log.info("최근 완료 강의 목록 조회 - 학생 ID: {}", student.getId());
        return courseRepository.findRecentCompletedCoursesByStudent(student)
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