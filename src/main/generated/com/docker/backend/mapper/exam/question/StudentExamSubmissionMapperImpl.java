package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.StudentAnswer;
import com.docker.backend.domain.exam.StudentExamStatus;
import com.docker.backend.domain.user.Student;
import com.docker.backend.dto.exam.StudentExamSubmissionDTO;
import com.docker.backend.dto.exam.question.StudentAnswerDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-09T10:17:01+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class StudentExamSubmissionMapperImpl implements StudentExamSubmissionMapper {

    @Autowired
    private StudentAnswerMapper studentAnswerMapper;

    @Override
    public StudentExamSubmissionDTO toDto(StudentExamStatus status) {
        if ( status == null ) {
            return null;
        }

        StudentExamSubmissionDTO studentExamSubmissionDTO = new StudentExamSubmissionDTO();

        studentExamSubmissionDTO.setStudentId( statusStudentId( status ) );
        studentExamSubmissionDTO.setStudentName( statusStudentName( status ) );
        studentExamSubmissionDTO.setSubmitted( status.isSubmitted() );
        studentExamSubmissionDTO.setTotalScore( status.getTotalScore() );
        studentExamSubmissionDTO.setAnswers( studentAnswerListToStudentAnswerDTOList( status.getAnswers() ) );

        return studentExamSubmissionDTO;
    }

    private Long statusStudentId(StudentExamStatus studentExamStatus) {
        Student student = studentExamStatus.getStudent();
        if ( student == null ) {
            return null;
        }
        return student.getId();
    }

    private String statusStudentName(StudentExamStatus studentExamStatus) {
        Student student = studentExamStatus.getStudent();
        if ( student == null ) {
            return null;
        }
        return student.getName();
    }

    protected List<StudentAnswerDTO> studentAnswerListToStudentAnswerDTOList(List<StudentAnswer> list) {
        if ( list == null ) {
            return null;
        }

        List<StudentAnswerDTO> list1 = new ArrayList<StudentAnswerDTO>( list.size() );
        for ( StudentAnswer studentAnswer : list ) {
            list1.add( studentAnswerMapper.toDto( studentAnswer ) );
        }

        return list1;
    }
}
