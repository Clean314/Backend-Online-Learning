package com.docker.backend.repository.exam;

import com.docker.backend.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    // 교수자 ID + 강의 ID로 특정 강의의 모든 시험 조회
    List<Exam> findByCourseIdAndCourse_Educator_Id(Long courseId, Long educatorId);

    // 강의 ID와 시험 ID로 특정 시험 조회
    Optional<Exam> findByCourseIdAndId(Long courseId, Long examId);

    Optional<Exam> findByCourseIdAndIdAndCourse_Educator_Id(Long courseId, Long examId, Long educatorId);
}
