package com.docker.backend.entity;

import com.docker.backend.enums.QuestionType;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Exam exam;

    private String content; // 문제 내용

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private Integer score;


    @ElementCollection
    private List<String> multipleChoices;

    // 정답 (주관식, 객관식)
    private String answer;

    // 정답 해설 (선택적)
    private String explanation;

    // 난이도, 이미지, 첨부파일 등

}
