package com.docker.backend.dto.exam.question;

import com.docker.backend.domain.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionForm {
    private Long id;
    private int number;
    private String content;
    private String answer;
    private int score;
    @JsonProperty("question_type")
    private QuestionType questionType;
    private List<String> choices;
}