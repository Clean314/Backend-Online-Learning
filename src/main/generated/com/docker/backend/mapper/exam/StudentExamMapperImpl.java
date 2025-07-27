package com.docker.backend.mapper.exam;

import com.docker.backend.domain.course.Course;
import com.docker.backend.domain.exam.Exam;
import com.docker.backend.dto.exam.StudentExamDTO;
import com.docker.backend.mapper.exam.question.StudentQuestionMapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-09T10:17:00+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class StudentExamMapperImpl implements StudentExamMapper {

    @Autowired
    private StudentQuestionMapper studentQuestionMapper;

    @Override
    public StudentExamDTO toDto(Exam exam) {
        if ( exam == null ) {
            return null;
        }

        StudentExamDTO studentExamDTO = new StudentExamDTO();

        studentExamDTO.setCourseId( examCourseId( exam ) );
        studentExamDTO.setId( exam.getId() );
        studentExamDTO.setTitle( exam.getTitle() );
        studentExamDTO.setDescription( exam.getDescription() );
        studentExamDTO.setStartTime( exam.getStartTime() );
        studentExamDTO.setEndTime( exam.getEndTime() );
        studentExamDTO.setStatus( exam.getStatus() );
        studentExamDTO.setQuestions( studentQuestionMapper.toDtoList( exam.getQuestions() ) );

        return studentExamDTO;
    }

    @Override
    public List<StudentExamDTO> toDtoList(List<Exam> byCourseId) {
        if ( byCourseId == null ) {
            return null;
        }

        List<StudentExamDTO> list = new ArrayList<StudentExamDTO>( byCourseId.size() );
        for ( Exam exam : byCourseId ) {
            list.add( toDto( exam ) );
        }

        return list;
    }

    private Long examCourseId(Exam exam) {
        Course course = exam.getCourse();
        if ( course == null ) {
            return null;
        }
        return course.getId();
    }
}
