package com.docker.backend.dto;

import com.docker.backend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnrollmentDTO {
    private String courseName;
    private String educatorName;
    private int point;
    private Status status;
}
