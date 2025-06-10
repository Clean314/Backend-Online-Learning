package com.docker.backend.dto.exam;

import com.docker.backend.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private String content;
    private Integer score;

    public static QuestionDTO of(Question question) {
        return new QuestionDTO(
                question.getId(),
                question.getContent(),
                question.getScore()
        );
    }
}
