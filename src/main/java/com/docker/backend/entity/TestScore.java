package com.docker.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class TestScore {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Course course;

}
