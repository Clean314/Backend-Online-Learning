package com.docker.backend.mapper.exam;

import com.docker.backend.domain.exam.StudentAnswer;
import com.docker.backend.domain.exam.StudentExamStatus;
import com.docker.backend.dto.exam.StudentExamSubmissionDTO;
import com.docker.backend.dto.exam.question.StudentAnswerDTO;
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
public class ExamSubmissionMapperImpl implements ExamSubmissionMapper {

    @Override
    public List<StudentExamSubmissionDTO> toDtoList(List<StudentExamStatus> statuses) {
        if ( statuses == null ) {
            return null;
        }

        List<StudentExamSubmissionDTO> list = new ArrayList<StudentExamSubmissionDTO>( statuses.size() );
        for ( StudentExamStatus studentExamStatus : statuses ) {
            list.add( studentExamStatusToStudentExamSubmissionDTO( studentExamStatus ) );
        }

        return list;
    }

    protected StudentAnswerDTO studentAnswerToStudentAnswerDTO(StudentAnswer studentAnswer) {
        if ( studentAnswer == null ) {
            return null;
        }

        StudentAnswerDTO studentAnswerDTO = new StudentAnswerDTO();

        studentAnswerDTO.setAnswer( studentAnswer.getAnswer() );
        studentAnswerDTO.setCorrect( studentAnswer.isCorrect() );
        studentAnswerDTO.setScore( studentAnswer.getScore() );

        return studentAnswerDTO;
    }

    protected List<StudentAnswerDTO> studentAnswerListToStudentAnswerDTOList(List<StudentAnswer> list) {
        if ( list == null ) {
            return null;
        }

        List<StudentAnswerDTO> list1 = new ArrayList<StudentAnswerDTO>( list.size() );
        for ( StudentAnswer studentAnswer : list ) {
            list1.add( studentAnswerToStudentAnswerDTO( studentAnswer ) );
        }

        return list1;
    }

    protected StudentExamSubmissionDTO studentExamStatusToStudentExamSubmissionDTO(StudentExamStatus studentExamStatus) {
        if ( studentExamStatus == null ) {
            return null;
        }

        StudentExamSubmissionDTO studentExamSubmissionDTO = new StudentExamSubmissionDTO();

        studentExamSubmissionDTO.setSubmitted( studentExamStatus.isSubmitted() );
        studentExamSubmissionDTO.setTotalScore( studentExamStatus.getTotalScore() );
        studentExamSubmissionDTO.setAnswers( studentAnswerListToStudentAnswerDTOList( studentExamStatus.getAnswers() ) );

        return studentExamSubmissionDTO;
    }
}
