package com.docker.backend.repository.course;

import com.docker.backend.entity.course.Course;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.entity.user.Student;
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

//    Optional<Course> findByStudent_IdAndId(Long studentId, Long courseId);


    List<Course> findAllByOrderByCreatedAtDesc();
    List<Course> findByCourseNameContaining(String courseName);

    Boolean existsByCourseName(String courseName);

    boolean existsByIdAndEducator_Id(Long id, Long educatorId);

    Optional<Course> findByIdAndEducator_Id(Long courseId, Long educatorId);

    List<Course> findTop4ByEducatorOrderByCreatedAtDesc(Educator educator);
    List<Course> findTop4ByEducatorOrderByUpdatedAtDesc(Educator educator);

    @Query("SELECT c FROM Course c WHERE c.educator.id = :educatorId ORDER BY c.createdAt DESC, c.id DESC LIMIT 4")
    List<Course> findTop4ByEducatorIdOrderByCreatedAtDesc(@Param("educatorId") Long educatorId);

    @Query("SELECT c FROM Course c WHERE c.educator.id = :educatorId AND c.createdAt != c.updatedAt ORDER BY c.updatedAt DESC, c.id DESC LIMIT 4")
    List<Course> findTop4ByEducatorIdOrderByUpdatedAtDesc(@Param("educatorId") Long educatorId);

    @Query("SELECT c FROM Course c JOIN c.enrollments e WHERE e.student = :student AND e.status = 'ENROLLED' ORDER BY e.createdAt DESC")
    List<Course> findRecentEnrolledCoursesByStudent(@Param("student") Student student);

    @Query("SELECT c FROM Course c JOIN c.enrollments e WHERE e.student = :student AND e.status = 'COMPLETED' ORDER BY e.updatedAt DESC")
    List<Course> findRecentCompletedCoursesByStudent(@Param("student") Student student);
}