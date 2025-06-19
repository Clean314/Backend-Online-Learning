package com.docker.backend.dto.exam;

import lombok.*;

import com.docker.backend.entity.exam.StudentAnswer;
import com.docker.backend.entity.exam.StudentExamStatus;

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

    public static StudentExamSubmissionDTO of(StudentExamStatus status, List<StudentAnswer> answers) {
        List<AnswerDTO> answerDTOs = answers.stream()
                .map(a -> new AnswerDTO(a.getQuestion().getId(), a.getAnswer(), a.isCorrect(), a.getScore()))
                .toList();

        return new StudentExamSubmissionDTO(
                status.getStudent().getId(),
                status.getStudent().getName(),
                status.isSubmitted(),
                status.getTotalScore(),
                answerDTOs
        );
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class AnswerDTO {
        private Long questionId;
        private String answer;
        private boolean correct;
        private int score;
    }
}
