package com.docker.backend.repository.exam.question;

import com.docker.backend.domain.exam.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByExamId(Long examId);
    Optional<Question> findByExamIdAndId(Long examId, Long questionId);
//    boolean existsByExamIdAndId(Long questionId);
    void deleteByExamIdAndId(Long examId, Long questionId);
}