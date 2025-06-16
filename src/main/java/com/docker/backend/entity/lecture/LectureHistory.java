package com.docker.backend.entity.lecture;

import com.docker.backend.entity.user.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class LectureHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Long studentId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Long lectureId;

    @Column(nullable = false)
    private Double watchedTime = 0.0;

    @Column(nullable = false)
    private Boolean attendance = false;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
