package com.docker.backend.entity.exam.question;

import com.docker.backend.entity.exam.Exam;
import com.docker.backend.enums.QuestionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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

    private List<Integer> choices = new ArrayList<>();

    private int score;

    private QuestionType type;
}