package com.docker.backend.dto.admin;

import com.docker.backend.entity.Course;
import com.docker.backend.enums.Difficulty;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminCourseDetailDTO {@JsonProperty("course_id")
private Long courseId;

    @JsonProperty("course_name")
    private String courseName;

    @JsonProperty("educator_name")
    private String educatorName;

    private String category;
    private Difficulty difficulty;
    private int point;

    @JsonProperty("max_enrollment")
    private int maxEnrollment;

    public AdminCourseDetailDTO(Course course){
        this.courseId = course.getId();
        this.courseName = course.getCourseName();
        this.educatorName = course.getEducator().getName();
        this.category = course.getCategory();
        this.difficulty = course.getDifficulty();
        this.point = course.getPoint();
        this.maxEnrollment = course.getMaxEnrollment();
    }
}
