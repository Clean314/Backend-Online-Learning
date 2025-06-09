package com.docker.backend.entity;

import jakarta.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Exam exam;

    private String content; // 문제 내용

    @OneToOne(fetch = FetchType.LAZY)
    private Answer answer;

    private Integer score;

}
