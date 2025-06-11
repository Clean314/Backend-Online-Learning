package com.docker.backend.enums;

import com.docker.backend.exception.GlobalExceptionHandler;

import java.util.Collections;
import java.util.Set;

public enum ExamStatus {
    COMPLETED(Collections.emptySet()),
    CANCELLED(Collections.emptySet()),
    IN_PROGRESS(Set.of(COMPLETED, CANCELLED)),
    PREPARING(Set.of(IN_PROGRESS, CANCELLED));

    private final Set<ExamStatus> allowedNext;

    ExamStatus(Set<ExamStatus> allowedNext) {
        this.allowedNext = Collections.unmodifiableSet(allowedNext);
    }

    public void validateTransition(ExamStatus to) {
        if (!allowedNext.contains(to)) {
            throw new GlobalExceptionHandler.InvalidExamStateException(this.name(), to.name());
        }
    }
}
