package com.docker.backend.repository.exam;

import com.docker.backend.domain.exam.StudentAnswer;
import com.docker.backend.domain.exam.StudentExamStatus;
import com.docker.backend.domain.exam.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {
    Optional<StudentAnswer> findByStudentExamStatusAndQuestion(StudentExamStatus status, Question question);

    List<StudentAnswer> findByStudentExamStatus(StudentExamStatus status);
}
