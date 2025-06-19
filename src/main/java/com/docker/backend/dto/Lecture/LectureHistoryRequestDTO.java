package com.docker.backend.dto.Lecture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureHistoryRequestDTO {
    private Long lectureId;
    private Double watchedTime;
    private Boolean attendance;
} 