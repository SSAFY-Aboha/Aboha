
package com.ssafy.aboha.abog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record AbogRequest(
        @NotNull(message = "관광지 식별자가 입력되지 않았습니다.")
        Integer attraction,
        @NotBlank(message = "제목이 입력되지 않았습니다.")
        String title,
        @NotBlank(message = "내용이 입력되지 않았습니다.")
        String content,
        @NotNull(message = "최소 1개의 이미지를 업로드해야 합니다.")
        @Size(min = 1, message = "최소 1개의 이미지를 업로드해야 합니다.")
        List<MultipartFile> imageFiles,
        @Size(min = 1, max = 3, message = "태그는 최소 1개부터 최대 3개까지 가능합니다.")
        List<String> tags
) {
}
