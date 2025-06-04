package com.docker.backend.repository.testScore;

import com.docker.backend.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByCourseIdAndEducatorId(Long courseId, Long educatorId);
//    Exam findByCourseIdAndEducatorIdAndExamId(Long courseId, Long id, Long ExamId);
    Exam findByCourseIdAndEducatorIdAndId(Long courseId, Long educatorId, Long examId);
}
