package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.Exam;
import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.dto.exam.EducatorExamDTO;
import com.docker.backend.mapper.exam.ExamBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ExamBaseMapper.class, EducatorQuestionMapper.class})
public interface EducatorExamMapper {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "questions", target = "questions")
    @Mapping(target = "scoreSum", expression = "java(getScoreSum(exam))")
    EducatorExamDTO toDto(Exam exam);

    default int getScoreSum(Exam exam) {
        return exam.getQuestions().stream()
                .mapToInt(Question::getScore)
                .sum();
    }
}