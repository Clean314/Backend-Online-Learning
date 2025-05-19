package com.docker.backend.entity;

import com.docker.backend.entity.user.Educator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @ManyToOne(fetch = FetchType.LAZY)
    private Educator educator;

    private int maxEnrollment;

//    @OneToMany
//    @JoinColumn(name = "enrollment_id", nullable = false)
//    private Enrollment enrollment;
//
//    @OneToMany(mappedBy = "course")
//    private List<Lecture> lectures;

}
