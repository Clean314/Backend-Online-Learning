package com.docker.backend.dto.exam.question;

import com.docker.backend.entity.exam.question.Question;

public class QuestionMapper {
    public static Question toEntity(QuestionCreateUpdateForm form) {
        Question question = new Question();
        question.setAnswer(form.getAnswer());
        question.setNumber(form.getNumber());
        question.setScore(form.getScore());
        question.setContent(form.getContent());
        return question;
    }
}
