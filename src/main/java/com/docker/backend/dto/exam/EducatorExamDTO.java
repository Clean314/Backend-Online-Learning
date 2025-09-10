package com.docker.backend.dto.exam;

import com.docker.backend.dto.exam.question.EducatorQuestionDTO;
import com.docker.backend.domain.exam.Exam;
import com.docker.backend.domain.enums.ExamStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Schema(description = "강사가 관리하는 시험 정보")
public class EducatorExamDTO {

    @Schema(description = "시험 ID", example = "1001")
    private Long id;

    @Schema(description = "강좌 ID", example = "101")
    private Long courseId;

    @Schema(description = "시험 제목", example = "중간고사")
    private String title;

    @Schema(description = "시험 설명", example = "자바 기초 개념을 평가하는 시험입니다.")
    private String description;

    @Schema(description = "시험 시작 시간", example = "2025-09-10T09:00:00")
    private LocalDateTime startTime;

    @Schema(description = "시험 종료 시간", example = "2025-09-10T11:00:00")
    private LocalDateTime endTime;

    @Schema(description = "시험 상태", example = "SCHEDULED")
    private ExamStatus status;

    @Schema(description = "시험 문항 목록")
    private List<EducatorQuestionDTO> questions;
}
