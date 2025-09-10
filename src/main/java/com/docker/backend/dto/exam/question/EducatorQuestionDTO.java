package com.docker.backend.dto.exam.question;

import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.domain.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "강사가 관리하는 시험 문항 정보")
public class EducatorQuestionDTO {

    @Schema(description = "문항 ID", example = "1")
    private Long id;

    @Schema(description = "문항 번호", example = "1")
    private int number;

    @Schema(description = "문항 내용", example = "다형성의 정의를 설명하시오.")
    private String content;

    @Schema(description = "정답", example = "객체가 여러 형태를 가질 수 있는 성질")
    private String answer;

    @Schema(description = "배점", example = "10")
    private int score;

    @JsonProperty("question_type")
    @Schema(description = "문항 유형 (객관식, 주관식 등)", example = "MULTIPLE_CHOICE")
    private QuestionType questionType;

    @Schema(description = "객관식 선택지", example = "[\"A. 상속\", \"B. 캡슐화\", \"C. 다형성\"]")
    private List<String> choices;
}
