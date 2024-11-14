package com.ssafy.aboha.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message = "닉네임이 입력되지 않았습니다.")
        @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하입니다.")
        String nickname,
        @NotBlank(message = "이메일이 입력되지 않았습니다.")
        @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+[.][0-9A-Za-z]+$",
                message = "잘못된 이메일 형식입니다.")
        String email,
        @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
                message = "비밀번호는 8자 이상의 영문, 숫자, 특수문자로 구성되어야 합니다.")
        String password
) {
}
