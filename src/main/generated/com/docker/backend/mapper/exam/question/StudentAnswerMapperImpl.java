package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.StudentAnswer;
import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.dto.exam.question.StudentAnswerDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-09T10:17:01+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class StudentAnswerMapperImpl implements StudentAnswerMapper {

    @Override
    public StudentAnswerDTO toDto(StudentAnswer answer) {
        if ( answer == null ) {
            return null;
        }

        StudentAnswerDTO studentAnswerDTO = new StudentAnswerDTO();

        studentAnswerDTO.setQuestionId( answerQuestionId( answer ) );
        studentAnswerDTO.setAnswer( answer.getAnswer() );
        studentAnswerDTO.setCorrect( answer.isCorrect() );
        studentAnswerDTO.setScore( answer.getScore() );

        return studentAnswerDTO;
    }

    private Long answerQuestionId(StudentAnswer studentAnswer) {
        Question question = studentAnswer.getQuestion();
        if ( question == null ) {
            return null;
        }
        return question.getId();
    }
}
