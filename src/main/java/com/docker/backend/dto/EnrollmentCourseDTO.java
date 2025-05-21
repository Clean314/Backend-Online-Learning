package com.docker.backend.dto;

import com.docker.backend.enums.Difficulty;
import com.docker.backend.enums.Status;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EnrollmentCourseDTO {
    private String courseCode;
    private String courseName;
    private String educatorName;
    private String category;
    private Difficulty difficulty;
    private Status status;
}