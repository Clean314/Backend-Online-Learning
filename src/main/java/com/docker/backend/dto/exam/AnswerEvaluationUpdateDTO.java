package com.docker.backend.dto.exam;

import lombok.Data;

@Data
public class AnswerEvaluationUpdateDTO {
    private boolean correct;
    private int score;
}