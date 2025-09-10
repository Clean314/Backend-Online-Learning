package com.docker.backend.dto.exam.question;

import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.domain.enums.QuestionType;
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
@Schema(description = "학생이 시험에서 보게 되는 문항 정보")
public class StudentQuestionDTO {

    @Schema(description = "문항 ID", example = "1")
    private Long id;

    @Schema(description = "문항 번호", example = "1")
    private int number;

    @Schema(description = "문항 내용", example = "다형성의 정의를 설명하시오.")
    private String content;

    @Schema(description = "배점", example = "10")
    private int score;

    @Schema(description = "문항 유형", example = "MULTIPLE_CHOICE")
    private QuestionType questionType;

    @Schema(description = "객관식 선택지", example = "[\"상속\", \"캡슐화\", \"다형성\"]")
    private List<String> choices;
}
