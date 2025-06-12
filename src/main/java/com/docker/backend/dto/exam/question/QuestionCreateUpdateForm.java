package com.docker.backend.dto.exam.question;

import lombok.Getter;

@Getter
public class QuestionCreateUpdateForm {
    private Long questionId;
    private Long courseId;
    private Long examId;
    private int number;
    private String content;
    private String answer;
    private int score;
}