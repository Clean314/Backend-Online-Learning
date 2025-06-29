package com.docker.backend.scheduler;

import com.docker.backend.domain.exam.Exam;
import com.docker.backend.domain.enums.ExamStatus;
import com.docker.backend.repository.exam.ExamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ExamStatusScheduler {

    private final ExamRepository examRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateExamStatuses() {
        LocalDateTime now = LocalDateTime.now();

        List<Exam> toInProgress = examRepository.findByStatusAndStartTimeBefore(ExamStatus.PREPARING, now);
        for (Exam exam : toInProgress) {
            exam.setStatus(ExamStatus.IN_PROGRESS);
        }

        List<Exam> toCompleted = examRepository.findByStatusAndEndTimeBefore(ExamStatus.IN_PROGRESS, now);
        for (Exam exam : toCompleted) {
            exam.setStatus(ExamStatus.COMPLETED);
        }

        examRepository.saveAll(Stream.concat(toInProgress.stream(), toCompleted.stream()).toList());
    }
}
