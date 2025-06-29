package com.docker.backend.dto.exam.question;

import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.domain.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentQuestionDTO {
    private Long id;
    private int number;
    private String content;
    private int score;
    private QuestionType questionType;
    private List<String> choices;

    public static StudentQuestionDTO of(Question q) {
        return new StudentQuestionDTO(
                q.getId(),
                q.getNumber(),
                q.getContent(),
                q.getScore(),
                q.getQuestionType(),
                q.getChoices()
        );
    }
}
