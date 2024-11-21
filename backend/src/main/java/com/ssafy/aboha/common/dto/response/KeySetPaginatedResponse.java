package com.ssafy.aboha.common.dto.response;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Slice;

// TODO: 키셋 페이지네이션 적용
@Builder
public record KeySetPaginatedResponse<T>(
    List<T> content,        // 실제 데이터 리스트
    boolean hasNext,        // 다음 페이지 여부
    Long lastSortValue,     // 마지막 정렬 값 (예: likeCount, reviewCount, viewCount)
    Integer lastId,         // 마지막으로 조회된 레코드의 ID (키셋 페이징용)
    int pageSize,           // 페이지 크기
    long totalElements      // 전체 데이터 개수
) {

    public static <T> KeySetPaginatedResponse<T> from(Slice<T> slice, Long lastSortValue, Integer lastId, long totalElements) {
        return KeySetPaginatedResponse.<T>builder()
            .content(slice.getContent())
            .hasNext(slice.hasNext())
            .lastSortValue(lastSortValue)
            .lastId(lastId)
            .pageSize(slice.getSize())
            .totalElements(totalElements)
            .build();
    }
}
