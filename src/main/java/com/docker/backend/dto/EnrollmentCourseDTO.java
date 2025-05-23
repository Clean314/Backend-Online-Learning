package com.docker.backend.dto;

import com.docker.backend.enums.Difficulty;
import com.docker.backend.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EnrollmentCourseDTO {

    @JsonProperty("course_id")
    private Long courseId;

    private String course_name;
    private String educator_name;
    private String category;
    private Difficulty difficulty;
    private int point;
    private Status status;
    private int available_enrollment;
    private int max_enrollment;

}
