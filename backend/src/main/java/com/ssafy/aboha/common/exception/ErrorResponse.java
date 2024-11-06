package com.ssafy.aboha.common.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ErrorResponse(HttpStatus status, int code, String message
) {

    public static ErrorResponse of(HttpStatus status, String message) {
        return new ErrorResponse(status, status.value(), message);
    }
}
