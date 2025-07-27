package com.docker.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(
                ex.getStatus().value(),
                ex.getStatus().getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(body, ex.getStatus());
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ErrorResponse body = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                errors,
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(
            Exception ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse body = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(body, status);
    }

    public static class NotFoundException extends ApiException {
        public NotFoundException(String message) {
            super(HttpStatus.NOT_FOUND, message);
        }
    }

    public static class BadRequestException extends ApiException {
        public BadRequestException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
    }

    public static class AccessDeniedException extends ApiException {
        public AccessDeniedException(String message) {
            super(HttpStatus.FORBIDDEN, message);
        }
    }

    public static class EnrollmentUnavailableException extends BadRequestException {
        public EnrollmentUnavailableException() {
            super("여석이 없습니다.");
        }
    }

    public static class SubmitExamDurationException extends BadRequestException {
        public SubmitExamDurationException(String start, String end, String now) {
            super(String.format("응시 가능한 시간 (%s) ~ (%s) 이 아닙니다. 현재 시간 : (%s)", start, end, now));
        }
    }

    public static class UpdateLimitExamException extends BadRequestException {
        public UpdateLimitExamException() {
            super("생성/변경/삭제하려는 시험은 현재보다 최소 24시간 이후여야 합니다.");
        }
    }

    public static class InvalidExamDurationException extends BadRequestException {
        public InvalidExamDurationException(String start, String end) {
            super(String.format("시험 종료 시간(%s)는 시험 시작 시간(%s)보다 최소 20분 이상이야 합니다.", end, start));
        }
    }

    public static class InvalidExamStateException extends BadRequestException {
        public InvalidExamStateException(String from, String to) {
            super(String.format("시험 상태를 %s에서 %s로 변경할 수 없습니다.", from, to));
        }
    }

}
