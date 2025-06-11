package com.docker.backend.dto.exam;

import com.docker.backend.entity.course.Course;
import com.docker.backend.entity.exam.Exam;
import com.docker.backend.entity.exam.Question;
import com.docker.backend.enums.ExamStatus;

import java.util.List;
import java.util.stream.Collectors;


public class ExamMapper {

    public static Exam toEntity(ExamCreateDTO dto, Course course) {
        Exam exam = new Exam();
        exam.setCourse(course);
        exam.setTitle(dto.getTitle());
        exam.setDescription(dto.getDescription());
        exam.setStartTime(dto.getStartTime());
        exam.setEndTime(dto.getEndTime());
        exam.setStatus(ExamStatus.PREPARING);

        List<Question> questionEntities = dto.getQuestions().stream()
                .map(qDto -> {
                    Question q = new Question();
                    q.setNumber(qDto.getNumber());
                    q.setContent(qDto.getContent());
                    q.setAnswer(qDto.getAnswer());
                    q.setScore(qDto.getScore());
                    q.setExam(exam);
                    return q;
                })
                .collect(Collectors.toList());

        exam.setQuestions(questionEntities);
        return exam;
    }
}
