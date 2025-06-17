package com.docker.backend.dto.exam.question;

import com.docker.backend.entity.exam.question.Question;
import com.docker.backend.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public static StudentQuestionDTO of(Question q) {
        return new StudentQuestionDTO(
                q.getId(),
                q.getNumber(),
                q.getContent(),
                q.getScore(),
                q.getType()
        );
    }
}
