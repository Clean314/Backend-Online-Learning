package com.docker.backend.dto;

import lombok.Data;

@Data
public class CourseCreateRequest {
    private String name;
    private int maxEnrollment;
    private int point;
}