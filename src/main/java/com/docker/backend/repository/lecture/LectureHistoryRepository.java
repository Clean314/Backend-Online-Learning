package com.docker.backend.repository.lecture;

import com.docker.backend.entity.lecture.LectureHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureHistoryRepository extends JpaRepository<LectureHistory, Long> {
}
