package com.docker.backend.dto.exam;

import com.docker.backend.domain.enums.ExamStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "강사가 수정하려는 시험 정보")
public class ExamUpdateDTO {

    @Schema(description = "시험 제목", example = "중간고사")
    private String title;

    @Schema(description = "시험 설명", example = "자바 기초 개념을 평가하는 시험입니다.")
    private String description;

    @Schema(description = "시험 시작 시간", example = "2025-09-10T09:00:00")
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @Schema(description = "시험 종료 시간", example = "2025-09-10T11:00:00")
    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @Schema(description = "시험 상태", example = "SCHEDULED")
    private ExamStatus status;
}
