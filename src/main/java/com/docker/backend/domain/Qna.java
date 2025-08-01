package com.docker.backend.domain;

import com.docker.backend.domain.lecture.Lecture;
import com.docker.backend.domain.user.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String content;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @ManyToOne
    private Member member_id;
    @ManyToOne
    private Lecture lecture_id;
}
