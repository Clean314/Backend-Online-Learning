package com.docker.backend.repository;

import com.docker.backend.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
