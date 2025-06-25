package com.docker.backend.mapper.exam;

import com.docker.backend.domain.enums.ExamStatus;
import com.docker.backend.domain.exam.Exam;
import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.dto.exam.EducatorExamDTO;
import com.docker.backend.dto.exam.ExamCreateDTO;
import com.docker.backend.dto.exam.ExamUpdateDTO;
import com.docker.backend.mapper.exam.question.EducatorQuestionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EducatorQuestionMapper.class})
public interface EducatorExamMapper {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(target = "scoreSum", expression = "java(getScoreSum(exam))")
    EducatorExamDTO toDto(Exam exam);

    @Mapping(source = "course.id", target = "courseId")
    Exam toEntity(ExamCreateDTO dto, Long courseId, ExamStatus examStatus);

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "exam.id", target = "examId")
    Exam toEntity(ExamUpdateDTO dto, Long courseId, Long examId);

//    @Mapping(target = "courseId", source = "course.id")
//    Exam toEntity(EducatorExamDTO dto);

    default int getScoreSum(Exam exam) {
        return exam.getQuestions().stream()
                .mapToInt(Question::getScore)
                .sum();
    }
}
