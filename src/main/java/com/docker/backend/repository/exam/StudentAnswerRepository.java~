package com.docker.backend.repository.exam;

import com.docker.backend.entity.exam.StudentAnswer;
import com.docker.backend.entity.exam.StudentExamStatus;
import com.docker.backend.entity.exam.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {
    Optional<StudentAnswer> findByStudentExamStatusAndQuestion(StudentExamStatus status, Question question);
}
