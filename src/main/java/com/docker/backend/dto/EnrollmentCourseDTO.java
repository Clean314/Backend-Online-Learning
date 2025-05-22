package com.docker.backend.dto;

import com.docker.backend.enums.Difficulty;
import com.docker.backend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EnrollmentCourseDTO {

    private String courseName;
    private String educatorName;
    private String category;
    private Difficulty difficulty;
    private Status status;
    private int availableEnrollment;
}
