package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.Exam;
import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.dto.exam.question.EducatorQuestionDTO;
import com.docker.backend.dto.exam.question.QuestionForm;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-09T10:17:01+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class EducatorQuestionMapperImpl implements EducatorQuestionMapper {

    @Override
    public EducatorQuestionDTO toDto(Question question) {
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

    @Override
    public List<EducatorQuestionDTO> toDtoList(List<Question> questions) {
        if ( questions == null ) {
            return null;
        }

        List<EducatorQuestionDTO> list = new ArrayList<EducatorQuestionDTO>( questions.size() );
        for ( Question question : questions ) {
            list.add( toDto( question ) );
        }

        return list;
    }

    @Override
    public Question toEntity(QuestionForm questionForm, Exam exam) {
        if ( questionForm == null && exam == null ) {
            return null;
        }

        Question question = new Question();

        if ( questionForm != null ) {
            question.setId( questionForm.getId() );
            question.setNumber( questionForm.getNumber() );
            question.setContent( questionForm.getContent() );
            question.setAnswer( questionForm.getAnswer() );
            List<String> list = questionForm.getChoices();
            if ( list != null ) {
                question.setChoices( new ArrayList<String>( list ) );
            }
            question.setScore( questionForm.getScore() );
            question.setQuestionType( questionForm.getQuestionType() );
        }
        question.setExam( exam );

        return question;
    }
}
