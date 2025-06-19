package com.docker.backend.service.lecture;

import com.docker.backend.dto.Lecture.AttendanceDTO;
import com.docker.backend.dto.course.CourseAttendanceDTO;
import com.docker.backend.entity.course.Course;
import com.docker.backend.entity.enrollment.Enrollment;
import com.docker.backend.entity.lecture.Lecture;
import com.docker.backend.entity.lecture.LectureHistory;
import com.docker.backend.entity.user.Member;
import com.docker.backend.entity.user.Student;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.repository.enrollment.EnrollmentRepository;
import com.docker.backend.repository.lecture.LectureHistoryRepository;
import com.docker.backend.repository.lecture.LectureRepository;
import com.docker.backend.repository.member.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureHistoryService {

    private final LectureHistoryRepository lectureHistoryRepository;
    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

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

    public Double avgAttendance(Long courseId) {
        List<Lecture> lectureList = lectureRepository.findAllByCourseId(courseId);  // Course 타입의 courseId를 추출해서 해당하는 모든 lecture 를 list 로 조회
        List<LectureHistory> historyList = lectureHistoryRepository.findAllByLectureIn(lectureList);   // 해당 강의들의 출석기록을 전부 조회하여 List 로 담음
        int lectureCount = lectureList.size();

        Map<Long, Long> studentAttendanceTrueCount = historyList.stream()
                .filter(l -> Boolean.TRUE.equals(l.getAttendance()))    // 출석이 true 인 경우만 필터링해서
                .collect(Collectors.groupingBy(
                        l -> l.getStudent().getId(),
                        Collectors.counting()       // id 기준으로 묶어서 카운트
                ));
        if (lectureCount == 0 || historyList.isEmpty()) { //lectureCount 가 0이거나 historyList 에서 해당하는 lecture 가 없을 때 나눗셈 방지
            return 0.0;
        }
        List<Double> attendanceTrueGroup = studentAttendanceTrueCount.values().stream()
                .map(count -> ((double)count/lectureCount) * 100.0)       // 각 학생의 출석 횟수를 전체 강의 수로 나누어 출석률 계산 후 리스트에 담음
                .toList();

        return attendanceTrueGroup.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);           // 학생별 출석률의 평균을 계산하여 반환
    }

    public List<CourseAttendanceDTO> getStudentAttendance(Long courseId){
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("강의를 찾을 수 없습니다."));
        List<Lecture> lectureList = lectureRepository.findAllByCourseId(courseId);
        int lectureCount = lectureList.size();

        if (lectureCount == 0) return Collections.emptyList();

        List<CourseAttendanceDTO> result = new ArrayList<>();

        List<Enrollment> enrollments = enrollmentRepository.findAllByCourseId(courseId);

        for (Enrollment enrollment : enrollments){
            Student student = enrollment.getStudent();

            // 학생이 해당 강의들 중 몇 개 출석했는지
            int attendanceCount = lectureHistoryRepository
                    .countByStudentAndLectureInAndAttendanceTrue(student, lectureList);

            CourseAttendanceDTO dto = new CourseAttendanceDTO();
            dto.setStudentName(student.getName());
            dto.setTotalCourse(lectureCount);
            dto.setAttendanceTrue(attendanceCount);
            dto.setAttendanceFalse(lectureCount - attendanceCount);
            dto.setAttendanceAvg((double) attendanceCount / lectureCount * 100);

            result.add(dto);
            }
        return result;
    }

    public AttendanceDTO attendance(Long lectureId){
        Optional<LectureHistory> time = lectureHistoryRepository.findById(lectureId); // 해당하는 lectureId를 LectureHistory 를 조회
        AttendanceDTO dto = new AttendanceDTO();
        dto.setWatchedTime(time.orElseThrow().getWatchedTime());
        return dto;
    }

    public List<AttendanceDTO> attendanceList(Long courseId){
        List<Lecture> lectures = lectureRepository.findAllByCourseId(courseId);
        List<AttendanceDTO> list = new ArrayList<>();
        for(Lecture lecture : lectures){
            List<LectureHistory> histories = lectureHistoryRepository.findAllByLecture(lecture);
            for (LectureHistory h : histories) {
                AttendanceDTO dto = new AttendanceDTO();
                dto.setWatchedTime(h.getWatchedTime());
                list.add(dto);
            }
        }
        return list;
    }
}

