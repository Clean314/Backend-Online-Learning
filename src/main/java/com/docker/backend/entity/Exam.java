package com.docker.backend.entity;

import com.docker.backend.entity.user.Educator;
import com.docker.backend.enums.ExamStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Exam {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    private Educator educator;

    private String title;
    private String description;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
//    private Integer durationMinutes;

    @Enumerated(EnumType.STRING)
    private ExamStatus status;

}
