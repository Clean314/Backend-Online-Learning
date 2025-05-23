package com.docker.backend.dto;

import com.docker.backend.enums.Difficulty;
import com.docker.backend.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EnrollmentCourseDTO {

    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("course_name")
    private String courseName;

    @JsonProperty("educator_name")
    private String educatorName;

    private String category;
    private Difficulty difficulty;
    private Status status;

    @JsonProperty("available_enrollment")
    private int availableEnrollment;
}
