package com.ssafy.aboha.common.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Slice;

import java.util.List;

@Builder
public record SimplifiedResponse<T>(
        List<T> content,   // 실제 데이터 리스트
        boolean hasNext,   // 다음 페이지 여부
        int pageNumber,    // 현재 페이지 번호
        int pageSize       // 페이지 크기
) {

    public static <T> SimplifiedResponse<T> from(Slice<T> slice) {
        return SimplifiedResponse.<T>builder()
                .content(slice.getContent())
                .hasNext(slice.hasNext())
                .pageNumber(slice.getNumber())
                .pageSize(slice.getSize())
                .build();
    }
}
