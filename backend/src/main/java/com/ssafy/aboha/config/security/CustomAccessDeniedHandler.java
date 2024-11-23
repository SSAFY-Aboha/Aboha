package com.ssafy.aboha.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.aboha.common.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    // ObjectMapper를 빈으로 주입받습니다.
    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(
        HttpServletRequest request,
        HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // ErrorResponse 인스턴스 생성
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");

        // 응답 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // ErrorResponse를 JSON으로 직렬화하여 응답 본문에 작성
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
        response.getOutputStream().flush(); // 응답 내용을 강제로 플러시
        response.getOutputStream().close(); // 스트림 닫기
    }
}
