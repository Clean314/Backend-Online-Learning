package com.docker.backend.dto.exam;

import com.docker.backend.domain.enums.ExamStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExamBaseDTO {
    private Long id;
    private String title;
    private String description;

    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    private LocalDateTime endTime;

    private ExamStatus status;

    @JsonProperty("course_id")
    private Long courseId;
}
