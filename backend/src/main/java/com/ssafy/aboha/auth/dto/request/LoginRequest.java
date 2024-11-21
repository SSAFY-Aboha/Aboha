package com.ssafy.aboha.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "이메일이 입력되지 않았습니다.")
        @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+[.][0-9A-Za-z]+$",
                message = "잘못된 이메일 형식입니다.")
        String email,
        @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
        String password
) {
}
