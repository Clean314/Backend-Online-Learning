package com.docker.backend.dto;

import com.docker.backend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnrollRequest {
    private Long courseId;
}
