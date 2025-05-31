package com.docker.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class TestScore {
    @Id
    @GeneratedValue
    private int id;
}
