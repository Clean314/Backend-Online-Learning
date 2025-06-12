package com.docker.backend.service.lecture;

import com.docker.backend.dto.Lecture.LectureHistoryDTO;
import com.docker.backend.entity.lecture.LectureHistory;
import com.docker.backend.entity.user.Student;
import com.docker.backend.repository.lecture.LectureHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureHistoryService {

    private LectureHistoryRepository lectureHistoryRepository;

    public void saveTimeLine(LectureHistory dto){
        lectureHistoryRepository.save(dto);
    }
}
