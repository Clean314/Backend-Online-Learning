package com.docker.backend.entity;

import com.docker.backend.entity.user.Student;
import com.docker.backend.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @Version
    private Long version; // 낙관적 락 (동시성 방지)

    public Enrollment(Student student, Course course, Status status) {
    }
}
