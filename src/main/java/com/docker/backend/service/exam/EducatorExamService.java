package com.docker.backend.service.exam;

import com.docker.backend.dto.exam.EducatorExamDTO;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.repository.exam.ExamRepository;
import com.docker.backend.repository.exam.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducatorExamService {
    private final CourseRepository courseRepository;
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;

    public List<EducatorExamDTO> getExams(Long educatorId, Long courseId) {
        // Fetch exams for the educator
        return examRepository.findExamsByEducatorId(educatorId);
    }

    private boolean isOwnerOfCourse(Long educatorId, Long courseId) {
        return courseRepository.existsByIdAndEducatorId(courseId, educatorId);
    }
}
