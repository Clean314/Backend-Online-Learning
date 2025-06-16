package com.docker.backend.service.lecture;

import com.docker.backend.dto.Lecture.LectureDTO;
import com.docker.backend.entity.course.Course;
import com.docker.backend.entity.lecture.Lecture;
import com.docker.backend.entity.lecture.LectureHistory;
import com.docker.backend.entity.user.Student;
import com.docker.backend.repository.lecture.LectureHistoryRepository;
import com.docker.backend.repository.lecture.LectureRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureHistoryService {

    private final LectureHistoryRepository lectureHistoryRepository;
    private final LectureRepository lectureRepository;

    // 실시간으로 프론트에서 iframe으로 10초? 정도 간격으로 데이터를 보내주는걸 저장
    public void saveTimeLine(Student student, LectureHistory dto){

        Lecture lecture = lectureRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("강의 없음"));

        LectureHistory history = lectureHistoryRepository
                .findByStudentAndLecture(student, lecture)
                .orElseThrow(() -> new IllegalArgumentException("수강 기록 없음"));

        history.setWatchedTime(dto.getWatchedTime());
        history.setAttendance(dto.getAttendance());

        lectureHistoryRepository.save(history);
    }

    public Double avgAttendance(Course courseId){
        List<Lecture> lectureList = lectureRepository.findAllByCourseId(courseId);  // 매개변수로 받은 courseId에 해당하는 lectureList lectureList 에 넣어 반환
        List<LectureHistory> lectureInStudent = lectureHistoryRepository.findAllByStudentId(courseId); // 해당하는 courseId의 StudentId를 lectureInStudent 에 넣어 반환
        int lectureCount = lectureList.size();

        if(lectureCount == 0 || lectureInStudent.isEmpty()){ //lectureCount 가 0이거나 courseId에 해당하는 studentId가 없을 때 나눗셈 방지
            return 0.0;
        }

        List<Long> lectureIds = lectureList.stream()
                .map(Lecture::getId)
                .toList();   // lecture id를 추출

        double total = 0.0;
        return 0.0;
    }
}

