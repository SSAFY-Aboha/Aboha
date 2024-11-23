package com.ssafy.aboha.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.aboha.common.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    // ObjectMapper를 빈으로 주입받습니다.
    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {

        // ErrorResponse 인스턴스 생성
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");

        // 응답 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // ErrorResponse를 JSON으로 직렬화하여 응답 본문에 작성
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
        response.getOutputStream().flush(); // 응답 내용을 강제로 플러시
        response.getOutputStream().close(); // 스트림 닫기
    }
}
