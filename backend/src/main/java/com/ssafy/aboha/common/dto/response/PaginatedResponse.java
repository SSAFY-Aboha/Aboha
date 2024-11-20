package com.ssafy.aboha.common.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Slice;

import java.util.List;

@Builder
public record PaginatedResponse<T>(
        List<T> content,   // 실제 데이터 리스트
        boolean hasNext,   // 다음 페이지 여부
        int pageNumber,    // 현재 페이지 번호
        int pageSize,      // 페이지 크기
        long totalElements // 전체 데이터 개수
) {

    public static <T> PaginatedResponse<T> from(Slice<T> slice, long totalElements) {
        return PaginatedResponse.<T>builder()
                .content(slice.getContent())
                .hasNext(slice.hasNext())
                .pageNumber(slice.getNumber())
                .pageSize(slice.getSize())
                .totalElements(totalElements)
                .build();
    }
}

