package com.docker.backend.dto.exam.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnswerResultDTO {
    private Long questionId;
    private String answer;
    private boolean correct;
    private int score;
}
