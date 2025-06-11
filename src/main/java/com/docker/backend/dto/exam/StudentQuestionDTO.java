package com.docker.backend.dto.exam;

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
}
