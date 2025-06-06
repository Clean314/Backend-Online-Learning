package com.docker.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    private Question question;

    private String correctAnswer;

}