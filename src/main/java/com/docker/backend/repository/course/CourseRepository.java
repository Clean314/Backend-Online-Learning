package com.docker.backend.repository.course;

import com.docker.backend.entity.course.Course;
import com.docker.backend.entity.user.Educator;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByEducatorId(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Course c WHERE c.id = :id")
    Optional<Course> findByIdForUpdate(@Param("id") Long id);

    Optional<Course> findByEducatorAndId(Educator educator, Long id);

    List<Course> findAllByOrderByCreatedAtDesc();
    List<Course> findByCourseNameContaining(String courseName);

    Boolean existsByCourseName(String courseName);

    boolean existsByIdAndEducator_Id(Long courseId, Long educatorId);

    Optional<Course> findByIdAndEducator_Id(Long courseId, Long educatorId);
}