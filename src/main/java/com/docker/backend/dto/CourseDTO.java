package com.docker.backend.dto;

import com.docker.backend.entity.Course;
import com.docker.backend.enums.Difficulty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CourseDTO {
    @JsonProperty("course_name")
    private String courseName;

    private String category;
    private Difficulty difficulty;
    private int point;
    private String description;
    private int maxEnrollment;

    public CourseDTO(Course course) {
        this.courseName = course.getCourseName();
        this.category = course.getCategory();
        this.difficulty = course.getDifficulty();
        this.description = course.getDescription();
        this.point = course.getPoint();
        this.maxEnrollment = course.getMaxEnrollment();
    }

}