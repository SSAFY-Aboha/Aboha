package com.ssafy.aboha.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 모든 필드 오류를 맵으로 수집
        Map<String, String> errors = new HashMap<>();
        for(FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        // 에러 메시지를 하나의 문자열로 결합 (첫 번째 오류 메시지 사용)
        String errorMessage = errors.values().stream().findFirst().orElse("유효성 검사 실패");

        return ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.BAD_REQUEST, errorMessage));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.BAD_REQUEST, "요청 본문이 비어 있습니다."));
    }

    // 409 Conflict
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.of(HttpStatus.CONFLICT, e.getMessage()));
    }

    // 500 Internal Server Error
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.internalServerError().body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 알 수 없는 문제가 발생했습니다."));
    }
}
