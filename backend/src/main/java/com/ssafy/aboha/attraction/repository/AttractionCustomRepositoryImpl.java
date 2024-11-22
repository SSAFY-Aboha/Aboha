package com.ssafy.aboha.attraction.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.domain.QAttraction;
import com.ssafy.aboha.attraction.domain.QGugun;
import com.ssafy.aboha.attraction.domain.QSido;
import com.ssafy.aboha.attraction.dto.response.AttractionInfo;
import com.ssafy.aboha.attraction.dto.response.AttractionSummary;
import com.ssafy.aboha.common.dto.response.KeySetPaginatedResponse;
import com.ssafy.aboha.common.dto.response.PaginatedResponse;
import com.ssafy.aboha.like.domain.QAttractionLike;
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
    public PaginatedResponse<AttractionInfo> findAll(Integer sidoCode, Integer gugunCode, Integer contentTypeId, String keyword, String sort, Pageable pageable) {
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
    public KeySetPaginatedResponse<AttractionInfo> findAlls(Integer sidoCode, Integer gugunCode,
        Integer contentTypeId, String keyword, String sort, Long lastSortValue, Integer lastId,
        Pageable pageable) {
        QAttraction qAttraction = QAttraction.attraction;
        QGugun qGugun = QGugun.gugun;
        QSido qSido = QSido.sido;

        BooleanBuilder builder = new BooleanBuilder();

        // 1. 필터 조건 적용
        if (sidoCode != null) {
            builder.and(qAttraction.sido.code.eq(sidoCode));
        }
        if (gugunCode != null) {
            builder.and(qAttraction.gugun.code.eq(gugunCode));
        }
        if (contentTypeId != null) {
            builder.and(qAttraction.contentType.id.eq(contentTypeId));
        }

        // 키워드 검색
        if (keyword != null && !keyword.trim().isEmpty()) {
            String trimmedKeyword = keyword.trim();
            builder.and(qAttraction.title.containsIgnoreCase(trimmedKeyword));
        }

        // 2. 전체 레코드 수 계산
        long totalElements = queryFactory
            .select(qAttraction.count())
            .from(qAttraction)
            .leftJoin(qAttraction.gugun, qGugun)
            .where(builder)
            .fetchOne();

        // 3. 키셋 페이징 조건 추가
        if (lastSortValue != null && lastId != null) {
            switch (sort) {
                case "POPULAR":
                    builder.and(qAttraction.likeCount.lt(lastSortValue))
                        .or(qAttraction.likeCount.eq(lastSortValue)
                            .and(qAttraction.id.gt(lastId)));
                    break;
                case "REVIEW":
                    builder.and(qAttraction.reviewCount.lt(lastSortValue))
                        .or(qAttraction.reviewCount.eq(lastSortValue)
                            .and(qAttraction.id.gt(lastId)));
                    break;
                case "VIEW":
                    builder.and(qAttraction.viewCount.lt(lastSortValue))
                        .or(qAttraction.viewCount.eq(lastSortValue)
                            .and(qAttraction.id.gt(lastId)));
                    break;
                default:
                    builder.and(qAttraction.id.gt(lastId));
                    break;
            }
        }

        // 4. 정렬 조건 결정
        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sort, qAttraction);

        // 5. 필요한 필드만 조회하는 프로젝션 사용
        List<AttractionInfo> pagedResults = queryFactory
            .select(Projections.constructor(AttractionInfo.class,
                qAttraction.id.as("id"),
                qAttraction.title.as("title"),
                qAttraction.sido.code.as("sidoCode"),
                qAttraction.sido.name.as("sidoName"),
                qAttraction.gugun.code.as("gugunCode"),
                qAttraction.gugun.name.as("gugunName"),
                qAttraction.firstImage1.as("image"), // 이미지 필드
                qAttraction.likeCount.as("likeCount"),
                qAttraction.viewCount.as("viewCount"),
                qAttraction.reviewCount.as("reviewCount")
            ))
            .from(qAttraction)
            .leftJoin(qAttraction.sido, qSido)
            .leftJoin(qAttraction.gugun, qGugun)
            .where(builder)
            .orderBy(orderSpecifier, qAttraction.id.asc()) // 동일한 정렬 값일 경우 id를 기준으로 정렬
            .distinct() // 중복 레코드 제거
            .limit(pageable.getPageSize() + 1) // 다음 페이지 존재 여부 확인을 위해 하나 더 조회
            .fetch();


        // 6. 다음 페이지 존재 여부 판단
        boolean hasNext = pagedResults.size() > pageable.getPageSize();

        if (hasNext) {
            pagedResults.remove(pagedResults.size() - 1); // 실제 데이터는 pageSize만큼 유지
        }

        // 7. 마지막 정렬 값과 마지막 ID 추출
        Long newLastSortValue = null;
        Integer newLastId = null;
        if (!pagedResults.isEmpty()) {
            AttractionInfo lastRecord = pagedResults.get(pagedResults.size() - 1);
            switch (sort) {
                case "POPULAR":
                    newLastSortValue = lastRecord.likeCount();
                    break;
                case "REVIEW":
                    newLastSortValue = lastRecord.reviewCount();
                    break;
                case "VIEW":
                    newLastSortValue = lastRecord.viewCount();
                    break;
                default:
                    newLastSortValue = (long) lastRecord.id();
                    break;
            }
            newLastId = lastRecord.id();
        }

        // 8. `KeySetPaginatedResponse` 반환
        return KeySetPaginatedResponse.<AttractionInfo>builder()
            .content(pagedResults)
            .hasNext(hasNext)
            .lastSortValue(newLastSortValue)
            .lastId(newLastId)
            .totalElements(totalElements)
            .build();
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

    @Override
    public List<Attraction> findByFilters(Integer sidoCode, List<Integer> gugunCodes, Integer contentTypeId) {
        QAttraction qAttraction = QAttraction.attraction;
        QGugun qGugun = QGugun.gugun;

        BooleanBuilder builder = new BooleanBuilder();

        // 조건 추가
        if (sidoCode != null) {
            builder.and(qAttraction.sido.code.eq(sidoCode));
            builder.and(qAttraction.gugun.sido.code.eq(sidoCode));
        }
        if (gugunCodes != null && !gugunCodes.isEmpty()) {
            builder.and(qAttraction.gugun.code.in(gugunCodes));
        }
        if (contentTypeId != null) {
            builder.and(qAttraction.contentType.id.eq(contentTypeId));
        }

        // 이미지가 존재하는 관광지만 필터링
        builder.and(qAttraction.firstImage1.isNotNull())
                .and(qAttraction.firstImage1.ne("")); // 이미지가 빈 문자열이 아닌 경우도 포함

        // 전체 데이터 조회 - 중복 제거를 위한 데이터 로드
        return queryFactory
                .selectFrom(qAttraction)
                .leftJoin(qAttraction.gugun, qGugun).fetchJoin()
                .where(builder)
                .fetch()
                .stream()
                .distinct()
                .toList();
    }

    @Override
    public KeySetPaginatedResponse<AttractionInfo> findByUserLiked(Integer userId, Pageable pageable) {
        QAttraction qAttraction = QAttraction.attraction;
        QAttractionLike qAttractionLike = QAttractionLike.attractionLike;
        QSido qSido = QSido.sido;
        QGugun qGugun = QGugun.gugun;

        // 3. 프로젝션을 사용하여 AttractionInfo DTO로 데이터 매핑
        List<AttractionInfo> pagedResults = queryFactory
                .select(Projections.constructor(AttractionInfo.class,
                        qAttraction.id,
                        qAttraction.title,
                        qSido.code,
                        qSido.name,
                        qGugun.code,
                        qGugun.name,
                        qAttraction.firstImage1,
                        qAttraction.likeCount,
                        qAttraction.viewCount,
                        qAttraction.reviewCount
                ))
                .from(qAttractionLike)
                .join(qAttractionLike.attraction, qAttraction)
                .leftJoin(qAttraction.sido, qSido)
                .leftJoin(qAttraction.gugun, qGugun)
                .where(qAttractionLike.user.id.eq(userId))
                .limit(pageable.getPageSize() + 1) // 다음 페이지 존재 여부 확인
                .fetch();


        // 6. 다음 페이지 존재 여부 판단
        boolean hasNext = pagedResults.size() > pageable.getPageSize();

        if (hasNext) {
            pagedResults.remove(pagedResults.size() - 1); // 실제 데이터는 pageSize만큼 유지
        }

        // 7. 마지막 정렬 값과 마지막 ID 추출
        AttractionInfo lastRecord = pagedResults.get(pagedResults.size() - 1);
        Long newLastSortValue = Long.valueOf(lastRecord.id()); // 정렬 기준에 따라 변경 가능
        Integer newLastId = lastRecord.id().intValue();
        // 8. `KeySetPaginatedResponse` 반환
        return KeySetPaginatedResponse.<AttractionInfo>builder()
                .content(pagedResults)
                .hasNext(hasNext)
                .lastSortValue(newLastSortValue)
                .lastId(newLastId)
                .totalElements(0)
                .build();
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
