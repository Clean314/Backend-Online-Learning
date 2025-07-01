package com.docker.backend.dto.exam;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentExamSubmissionDTO {
    private Long studentId;
    private String studentName;
    private boolean submitted;
    private int totalScore;
    private List<AnswerDTO> answers;
}
