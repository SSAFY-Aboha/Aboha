package com.ssafy.aboha.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NicknameRequest(
        @NotBlank(message = "닉네임이 입력되지 않았습니다.")
        @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하입니다.")
        String nickname
) {
}
