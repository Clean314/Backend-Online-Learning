package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.dto.exam.question.StudentQuestionDTO;
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
public class StudentQuestionMapperImpl implements StudentQuestionMapper {

    @Override
    public StudentQuestionDTO toDto(Question question) {
        if ( question == null ) {
            return null;
        }

        StudentQuestionDTO studentQuestionDTO = new StudentQuestionDTO();

        return studentQuestionDTO;
    }

    @Override
    public List<StudentQuestionDTO> toDtoList(List<Question> questions) {
        if ( questions == null ) {
            return null;
        }

        List<StudentQuestionDTO> list = new ArrayList<StudentQuestionDTO>( questions.size() );
        for ( Question question : questions ) {
            list.add( toDto( question ) );
        }

        return list;
    }
}
