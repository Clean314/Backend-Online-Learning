package com.docker.backend.dto.exam;

import com.docker.backend.entity.exam.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentQuestionDTO {
    private int number;
    private String content;
    private int score;

    public static StudentQuestionDTO of(Question q) {
        return new StudentQuestionDTO(
                q.getNumber(),
                q.getContent(),
                q.getScore()
        );
    }
}
