package com.docker.backend.repository.testScore;

import com.docker.backend.entity.Course;
import com.docker.backend.entity.TestScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestScoreRepository extends JpaRepository<TestScore, Long> {
    List<TestScore> findByCourseId(Long courseId);
}
