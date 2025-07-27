package com.docker.backend.dto.exam.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentAnswerDTO {
    private Long questionId;
    private String answer;
    private boolean isCorrect;
    private int score;
}