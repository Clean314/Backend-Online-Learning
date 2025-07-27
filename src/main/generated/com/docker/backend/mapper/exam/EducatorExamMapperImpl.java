package com.docker.backend.mapper.exam;

import com.docker.backend.domain.course.Course;
import com.docker.backend.domain.enums.ExamStatus;
import com.docker.backend.domain.exam.Exam;
import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.dto.exam.EducatorExamDTO;
import com.docker.backend.dto.exam.ExamCreateDTO;
import com.docker.backend.dto.exam.ExamUpdateDTO;
import com.docker.backend.dto.exam.question.EducatorQuestionDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-09T10:23:04+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class EducatorExamMapperImpl implements EducatorExamMapper {

    @Override
    public EducatorExamDTO toDto(Exam exam) {
        if ( exam == null ) {
            return null;
        }

        EducatorExamDTO educatorExamDTO = new EducatorExamDTO();

        educatorExamDTO.setCourseId( examCourseId( exam ) );
        educatorExamDTO.setId( exam.getId() );
        educatorExamDTO.setTitle( exam.getTitle() );
        educatorExamDTO.setDescription( exam.getDescription() );
        educatorExamDTO.setStartTime( exam.getStartTime() );
        educatorExamDTO.setEndTime( exam.getEndTime() );
        educatorExamDTO.setStatus( exam.getStatus() );
        educatorExamDTO.setQuestions( questionListToEducatorQuestionDTOList( exam.getQuestions() ) );

        return educatorExamDTO;
    }

    @Override
    public List<EducatorExamDTO> toDtoList(List<Exam> byCourseId) {
        if ( byCourseId == null ) {
            return null;
        }

        List<EducatorExamDTO> list = new ArrayList<EducatorExamDTO>( byCourseId.size() );
        for ( Exam exam : byCourseId ) {
            list.add( toDto( exam ) );
        }

        return list;
    }

    @Override
    public Exam toEntity(ExamCreateDTO dto, Course course) {
        if ( dto == null && course == null ) {
            return null;
        }

        Exam exam = new Exam();

        if ( dto != null ) {
            exam.setDescription( dto.getDescription() );
            exam.setTitle( dto.getTitle() );
            exam.setStartTime( dto.getStartTime() );
            exam.setEndTime( dto.getEndTime() );
        }
        if ( course != null ) {
            exam.setId( course.getId() );
        }
        exam.setStatus( ExamStatus.PREPARING );

        return exam;
    }

    @Override
    public Exam toEntity(ExamUpdateDTO dto, Course course, Long examId) {
        if ( dto == null && course == null && examId == null ) {
            return null;
        }

        Exam exam = new Exam();

        if ( dto != null ) {
            exam.setDescription( dto.getDescription() );
            exam.setTitle( dto.getTitle() );
            exam.setStartTime( dto.getStartTime() );
            exam.setEndTime( dto.getEndTime() );
            exam.setStatus( dto.getStatus() );
        }
        exam.setCourse( course );
        exam.setId( examId );

        return exam;
    }

    private Long examCourseId(Exam exam) {
        Course course = exam.getCourse();
        if ( course == null ) {
            return null;
        }
        return course.getId();
    }

    protected EducatorQuestionDTO questionToEducatorQuestionDTO(Question question) {
        if ( question == null ) {
            return null;
        }

        EducatorQuestionDTO educatorQuestionDTO = new EducatorQuestionDTO();

        educatorQuestionDTO.setId( question.getId() );
        educatorQuestionDTO.setNumber( question.getNumber() );
        educatorQuestionDTO.setContent( question.getContent() );
        educatorQuestionDTO.setAnswer( question.getAnswer() );
        educatorQuestionDTO.setScore( question.getScore() );
        educatorQuestionDTO.setQuestionType( question.getQuestionType() );
        List<String> list = question.getChoices();
        if ( list != null ) {
            educatorQuestionDTO.setChoices( new ArrayList<String>( list ) );
        }

        return educatorQuestionDTO;
    }

    protected List<EducatorQuestionDTO> questionListToEducatorQuestionDTOList(List<Question> list) {
        if ( list == null ) {
            return null;
        }

        List<EducatorQuestionDTO> list1 = new ArrayList<EducatorQuestionDTO>( list.size() );
        for ( Question question : list ) {
            list1.add( questionToEducatorQuestionDTO( question ) );
        }

        return list1;
    }
}
