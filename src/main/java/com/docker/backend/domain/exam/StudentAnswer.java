package com.docker.backend.domain.exam;

import com.docker.backend.domain.exam.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StudentAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private StudentExamStatus studentExamStatus;

    @ManyToOne
    private Question question;

    private String answer;

    private boolean isCorrect;

    private int score;
}
