package com.docker.backend.entity;

import com.docker.backend.entity.user.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @ManyToOne
    private Course course_id;
    @ManyToOne
    private Member member_id;

}
