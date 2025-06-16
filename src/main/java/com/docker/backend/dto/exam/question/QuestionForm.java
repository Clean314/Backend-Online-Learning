package com.docker.backend.dto.exam.question;

import lombok.Getter;

@Getter
public class QuestionForm {
    private int number;
    private String content;
    private String answer;
    private int score;
}