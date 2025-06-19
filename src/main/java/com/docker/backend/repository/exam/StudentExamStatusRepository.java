package com.docker.backend.repository.exam;

import com.docker.backend.entity.exam.StudentExamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentExamStatusRepository extends JpaRepository<StudentExamStatus, Long> {
    Optional<StudentExamStatus> findByStudentIdAndExamId(Long studentId, Long examId);
}
