package com.docker.backend.domain;

import com.docker.backend.domain.lecture.Lecture;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String content;

    private LocalDateTime cratedAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    private Lecture lectureId;
}
