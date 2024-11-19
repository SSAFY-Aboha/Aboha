package com.ssafy.aboha.attraction.dto;

import org.springframework.data.domain.Sort;

public enum SortType {
    NEW(Sort.by(Sort.Order.desc("id"))), // 최신순
    POPULAR(Sort.by(Sort.Order.desc("likeCount"))), // 좋아요순
    REVIEW(Sort.by(Sort.Order.desc("reviewCount"))), // 리뷰순
    VIEW(Sort.by(Sort.Order.desc("viewCount"))); // 조회수순

    private final Sort sort;

    SortType(Sort sort) {
        this.sort = sort;
    }

    public Sort getSort() {
        return sort;
    }

    public static Sort from(String sort) {
        // Enum 값 매칭, 기본값은 NEW
        for (SortType attractionSort : SortType.values()) {
            if (attractionSort.name().equalsIgnoreCase(sort)) {
                return attractionSort.getSort();
            }
        }
        return NEW.getSort(); // 기본값
    }
}
