package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.dto.exam.question.QuestionForm;

public class QuestionMapper {
    public static Question toEntity(QuestionForm form) {
        Question question = new Question();
        question.setAnswer(form.getAnswer());
        question.setNumber(form.getNumber());
        question.setScore(form.getScore());
        question.setContent(form.getContent());
        question.setQuestionType(form.getQuestionType());
        question.setChoices(form.getChoices());
        return question;
    }
}
