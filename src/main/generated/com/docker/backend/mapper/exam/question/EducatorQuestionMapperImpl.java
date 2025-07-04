package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.dto.exam.question.EducatorQuestionDTO;
import com.docker.backend.dto.exam.question.QuestionForm;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-04T15:25:29+0900",
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
    public Question toEntity(QuestionForm questionForm) {
        if ( questionForm == null ) {
            return null;
        }

        Question question = new Question();

        return question;
    }
}
