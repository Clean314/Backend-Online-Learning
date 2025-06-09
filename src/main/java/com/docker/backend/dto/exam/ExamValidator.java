package com.docker.backend.dto.exam;

import java.time.Duration;
import java.time.LocalDateTime;

public class ExamValidator {

    public static void validateSchedule(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("시험 종료 시간이 시작 시간보다 빠를 수 없습니다.");
        }
        if (Duration.between(LocalDateTime.now(), start).toHours() < 24) {
            throw new IllegalStateException("시험 시작은 최소 24시간 이후여야 합니다.");
        }
    }
}
