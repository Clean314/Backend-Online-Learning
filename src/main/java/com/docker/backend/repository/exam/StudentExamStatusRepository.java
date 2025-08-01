package com.docker.backend.repository.exam;

import com.docker.backend.domain.exam.StudentExamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentExamStatusRepository extends JpaRepository<StudentExamStatus, Long> {
    Optional<StudentExamStatus> findByStudentIdAndExamId(Long studentId, Long examId);

    List<StudentExamStatus> findByExamId(Long examId);
}
