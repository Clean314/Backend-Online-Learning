package com.docker.backend.dto.course;

import com.docker.backend.domain.course.Course;
import com.docker.backend.domain.enums.Difficulty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("course_name")
    private String courseName;

    @JsonProperty("educator_name")
    private String educatorName;

    private String category;
    private Difficulty difficulty;
    private int point;
    private String description;

    @JsonProperty("max_enrollment")
    private int maxEnrollment;

    @JsonProperty("available_enrollment")
    private int availableEnrollment;
}