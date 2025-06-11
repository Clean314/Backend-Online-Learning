package com.docker.backend.dto.exam;

import com.docker.backend.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EducatorQuestionDTO {
    private int number;
    private String content;
    private String answer;
    private int score;

    public static EducatorQuestionDTO of(Question q) {
        return new EducatorQuestionDTO(
                q.getNumber(),
                q.getContent(),
                q.getAnswer(),
                q.getScore()
        );
    }
}