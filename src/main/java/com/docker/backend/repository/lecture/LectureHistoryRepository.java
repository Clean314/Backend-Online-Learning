package com.docker.backend.repository.lecture;

import com.docker.backend.domain.lecture.Lecture;
import com.docker.backend.domain.lecture.LectureHistory;
import com.docker.backend.domain.user.Student;
import com.docker.backend.service.lecture.LectureHistoryService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureHistoryRepository extends JpaRepository<LectureHistory, Long> {
    Optional<LectureHistory> findByStudentAndLecture(Student student, Lecture Lecture);
    List<LectureHistory> findAllByLectureIn(List<Lecture> lectureList);
    int countByStudentAndLectureInAndAttendanceTrue(Student student, List<Lecture> lectureList);
    List<LectureHistory> findAllByLecture(Lecture lecture);
    List<LectureHistory> findAllByLectureId(Long lectureId);

    LectureHistoryService findByLectureId(Long lectureId);
}
