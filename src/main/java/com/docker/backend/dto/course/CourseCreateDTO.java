package com.docker.backend.dto.course;

import com.docker.backend.domain.enums.Difficulty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CourseCreateDTO {
    @JsonProperty("course_name")
    private String courseName;
    private String category;
    private Difficulty difficulty;
    private int point;
    private String description;
    @JsonProperty("max_enrollment")
    private int maxEnrollment;
}