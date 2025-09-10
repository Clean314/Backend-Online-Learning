package com.docker.backend.dto.exam;

import com.docker.backend.dto.exam.question.StudentAnswerDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "학생의 시험 제출 결과")
public class StudentExamSubmissionDTO {

    @Schema(description = "학생 ID", example = "20230001")
    private Long studentId;

    @Schema(description = "학생 이름", example = "김철수")
    private String studentName;

    @Schema(description = "시험 제출 여부", example = "true")
    private boolean submitted;

    @Schema(description = "총 점수", example = "85")
    private int totalScore;

    @Schema(description = "학생 답안 목록")
    private List<StudentAnswerDTO> answers;
}
