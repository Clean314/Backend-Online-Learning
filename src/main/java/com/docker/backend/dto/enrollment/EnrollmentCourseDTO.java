package com.docker.backend.dto.enrollment;

import com.docker.backend.domain.enums.Difficulty;
import com.docker.backend.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "학생이 조회할 수 있는 강좌 정보")
public class EnrollmentCourseDTO {

    @JsonProperty("course_id")
    @Schema(description = "강좌 ID", example = "101")
    private Long id;

    @JsonProperty("course_name")
    @Schema(description = "강좌 이름", example = "객체지향 프로그래밍")
    private String courseName;

    @JsonProperty("educator_name")
    @Schema(description = "강사 이름", example = "홍길동")
    private String educatorName;

    @Schema(description = "카테고리", example = "컴퓨터공학")
    private String category;

    @Schema(description = "난이도 (EASY, MEDIUM, HARD)", example = "MEDIUM")
    private Difficulty difficulty;

    @Schema(description = "수강 상태 (ENROLLED, COMPLETED, AVAILABLE)", example = "ENROLLED")
    private Status status;

    @Schema(description = "강좌 포인트", example = "3")
    private int point;

    @Schema(description = "최대 수강 인원", example = "50")
    private int maxEnrollment;

    @Schema(description = "현재 남은 수강 가능 인원", example = "10")
    private int availableEnrollment;

    @Schema(description = "강좌 설명", example = "이 강좌는 자바 객체지향 개념을 학습합니다.")
    private String description;
}
