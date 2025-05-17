package com.docker.backend.repository;

import com.docker.backend.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByTeacherId(Long id);

    List<Lecture> findByStudentId(Long id);
}
