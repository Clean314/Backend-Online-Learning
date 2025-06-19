package com.docker.backend.dto.exam.question;

import com.docker.backend.entity.exam.question.Question;
import com.docker.backend.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EducatorQuestionDTO {
    private Long id;
    private int number;
    private String content;
    private String answer;
    private int score;
    @JsonProperty("value = question_type")
    private QuestionType questionType;
    private List<String> choices;

    public static EducatorQuestionDTO of(Question q) {
        return new EducatorQuestionDTO(
                q.getId(),
                q.getNumber(),
                q.getContent(),
                q.getAnswer(),
                q.getScore(),
                q.getQuestionType(),
                q.getChoices()
        );
    }
}