package com.docker.backend.dto.course;

import com.docker.backend.domain.course.Course;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 최근 강의 목록 조회용 DTO
 * - 강의 ID
 * - 강의명
 * - 강사명
 * - 카테고리
 * - 생성일시
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentCourseDTO {
    private Long id;
    
    @JsonProperty("name")
    private String courseName;
    
    @JsonProperty("educator_name")
    private String educatorName;
    
    private String category;
    private LocalDateTime createdAt;

    public RecentCourseDTO(Course course) {
        this.id = course.getId();
        this.courseName = course.getCourseName();
        this.educatorName = course.getEducator().getName();
        this.category = course.getCategory();
        this.createdAt = course.getCreatedAt();
    }
} 