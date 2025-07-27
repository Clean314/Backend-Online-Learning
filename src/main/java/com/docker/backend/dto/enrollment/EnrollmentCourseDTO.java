package com.docker.backend.dto.enrollment;

import com.docker.backend.domain.enums.Difficulty;
import com.docker.backend.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentCourseDTO {

    @JsonProperty("course_id")
    private Long id;

    @JsonProperty("course_name")
    private String courseName;

    @JsonProperty("educator_name")
    private String educatorName;

    private String category;
    private Difficulty difficulty;
    private Status status;

    private int point;
    private int maxEnrollment;
    private int availableEnrollment;
    private String description;
}