package com.docker.backend.domain.exam.question;

import com.docker.backend.domain.exam.Exam;
import com.docker.backend.domain.enums.QuestionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    @ManyToOne(fetch = FetchType.LAZY)
    private Exam exam;

    private String content;

    private String answer;

    @ElementCollection
    @CollectionTable(name = "question_choices", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "choice")
    private List<String> choices;

    private int score;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
}