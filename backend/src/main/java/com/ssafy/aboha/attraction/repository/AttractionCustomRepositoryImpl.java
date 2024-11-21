package com.ssafy.aboha.attraction.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.domain.QAttraction;
import com.ssafy.aboha.attraction.domain.QGugun;
import com.ssafy.aboha.attraction.dto.response.AttractionInfo;
import com.ssafy.aboha.attraction.dto.response.AttractionSummary;
import com.ssafy.aboha.common.dto.response.PaginatedResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AttractionCustomRepositoryImpl implements AttractionCustomRepository {

    private final JPAQueryFactory queryFactory;

    public AttractionCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public PaginatedResponse<AttractionInfo> findByFilters(Integer sidoCode, Integer gugunCode, Integer contentTypeId, String keyword, String sort, Pageable pageable) {
        QAttraction qAttraction = QAttraction.attraction;
        QGugun qGugun = QGugun.gugun;

        BooleanBuilder builder = new BooleanBuilder();

        // 조건 추가
        if (sidoCode != null) {
            builder.and(qAttraction.sido.code.eq(sidoCode));
            builder.and(qAttraction.gugun.sido.code.eq(sidoCode));
        }
        if (gugunCode != null) {
            builder.and(qAttraction.gugun.code.eq(gugunCode));
        }
        if (contentTypeId != null) {
            builder.and(qAttraction.contentType.id.eq(contentTypeId));
        }

        // 키워드 검색 (앞뒤에 % 추가)
        if (keyword != null && !keyword.trim().isEmpty()) {
            String trimmedKeyword = keyword.trim();
            builder.and(qAttraction.title.containsIgnoreCase(trimmedKeyword));
        }

        // 정렬 조건 적용
        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sort, qAttraction);

        // 전체 데이터 조회 - 중복 제거를 위한 데이터 로드
        List<Attraction> results = queryFactory
                .selectFrom(qAttraction)
                .leftJoin(qAttraction.gugun, qGugun).fetchJoin()
                .where(builder)
                .orderBy(orderSpecifier)
                .fetch()
                .stream()
                .distinct()
                .toList();

        // 중복 제거된 데이터에서 offset과 limit을 적용
        int offset = (int) pageable.getOffset();
        int limit = pageable.getPageSize();

        List<AttractionInfo> pagedResults = results.stream()
                .skip(offset) // offset 적용
                .limit(limit) // limit 적용
                .map(AttractionInfo::from) // Attraction을 AttractionInfo로 매핑
                .toList();

        // 다음 페이지 여부 판단
        boolean hasNext = results.size() > offset + limit;
        long totalElements = results.size();

        // Slice 객체 생성
        Slice<AttractionInfo> slice = new SliceImpl<>(pagedResults, pageable, hasNext);

        // PaginatedResponse 반환
        return PaginatedResponse.from(slice, totalElements);
    }

    @Override
    public Optional<Attraction> findByAttractionId(Integer id) {
        QAttraction qAttraction = QAttraction.attraction;
        QGugun qGugun = QGugun.gugun;

        return Optional.ofNullable(
            queryFactory
                .selectFrom(qAttraction)
                .leftJoin(qAttraction.gugun, qGugun).fetchJoin()
                .where(qAttraction.id.eq(id))
                .limit(1)
                .fetchOne()
        );
    }

    @Override
    public List<AttractionSummary> findByTitle(String title) {
        QAttraction qAttraction = QAttraction.attraction;
        QGugun qGugun = QGugun.gugun;

        List<Attraction> results = queryFactory
                .selectFrom(qAttraction)
                .leftJoin(qAttraction.gugun, qGugun).fetchJoin()
                .where(qAttraction.title.containsIgnoreCase(title))
                .fetch()
                .stream()
                .distinct()
                .toList();

        return results.stream()
                .map(AttractionSummary::from)
                .toList();
    }

    private OrderSpecifier<?> getOrderSpecifier(String sort, QAttraction qAttraction) {
        return switch (sort) {
            case "POPULAR" -> qAttraction.likeCount.desc();
            case "REVIEW" -> qAttraction.reviewCount.desc();
            case "VIEW" -> qAttraction.viewCount.desc();
            default -> qAttraction.id.asc(); // 기본값: 최신순(이지만, 오름차순으로)
        };
    }

}
