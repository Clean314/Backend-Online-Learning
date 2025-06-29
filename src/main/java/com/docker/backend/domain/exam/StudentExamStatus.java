package com.docker.backend.domain.exam;

import com.docker.backend.domain.user.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class StudentExamStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Exam exam;

    private boolean submitted;
    private LocalDateTime submittedAt;
    private int totalScore;

    @OneToMany(mappedBy = "studentExamStatus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentAnswer> answers = new ArrayList<>();
}