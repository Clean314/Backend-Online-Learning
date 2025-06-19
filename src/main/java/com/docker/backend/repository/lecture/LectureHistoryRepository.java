package com.docker.backend.repository.lecture;

import com.docker.backend.entity.course.Course;
import com.docker.backend.entity.enrollment.Enrollment;
import com.docker.backend.entity.lecture.Lecture;
import com.docker.backend.entity.lecture.LectureHistory;
import com.docker.backend.entity.user.Member;
import com.docker.backend.entity.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LectureHistoryRepository extends JpaRepository<LectureHistory, Long> {
    Optional<LectureHistory> findByStudentAndLecture(Student student, Lecture Lecture);
    List<LectureHistory> findAllByLectureIn(List<Lecture> lectureIds);
    int countByStudentAndLectureInAndAttendanceTrue(Student student, List<Lecture> lectureList);
    LectureHistory findByLectureId(Long lectureId);
}
