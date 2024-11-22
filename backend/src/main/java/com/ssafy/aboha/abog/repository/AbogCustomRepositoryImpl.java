package com.ssafy.aboha.abog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.domain.QAbog;
import com.ssafy.aboha.abog.dto.response.MyAbogResponse;
import com.ssafy.aboha.attraction.domain.QAttraction;
import com.ssafy.aboha.attraction.domain.QGugun;
import com.ssafy.aboha.attraction.domain.QSido;
import com.ssafy.aboha.common.dto.response.KeySetPaginatedResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AbogCustomRepositoryImpl implements AbogCustomRepository {

    private final JPAQueryFactory queryFactory;

    public AbogCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Abog> findByAbogId(Integer id) {
        QAbog qAbog = QAbog.abog;
        QAttraction qAttraction = QAttraction.attraction;
        QGugun qGugun = QGugun.gugun;
        QSido qSido = QSido.sido;

        return Optional.ofNullable(
                queryFactory
                        .selectDistinct(qAbog)
                        .from(qAbog)
                        .leftJoin(qAbog.attraction, qAttraction).fetchJoin()
                        .leftJoin(qAttraction.gugun, qGugun).fetchJoin()
                        .leftJoin(qGugun.sido, qSido).fetchJoin() // Sido와 fetch join
                        .where(qAbog.id.eq(id))
                        .fetchOne()
        );
    }

    @Override
    public KeySetPaginatedResponse<MyAbogResponse> findByUserAbog(Integer userId, Pageable pageable) {
        QAttraction qAttraction = QAttraction.attraction;
        QAbog qAbog = QAbog.abog;

        // Abog와 Attraction 데이터 조회 및 DTO 매핑
        List<MyAbogResponse> pagedResults = queryFactory
                .selectFrom(qAbog)
                .join(qAbog.attraction, qAttraction)
                .where(qAbog.user.id.eq(userId))
                .orderBy(qAbog.createdAt.desc())
                .limit(pageable.getPageSize() + 1) // 다음 페이지 존재 여부 확인
                .fetch()
                .stream()
                .map(abog -> MyAbogResponse.of(abog.getAttraction(), abog)) // of 메서드를 통해 매핑
                .toList();

        // 다음 페이지 존재 여부 판단
        boolean hasNext = pagedResults.size() > pageable.getPageSize();

        if (hasNext) {
            pagedResults.remove(pagedResults.size() - 1); // 실제 데이터는 pageSize만큼 유지
        }

        // 마지막 정렬 값과 마지막 ID 추출
        MyAbogResponse lastRecord = pagedResults.get(pagedResults.size() - 1);
        Integer newLastId = lastRecord.abogId();

        // Paginated Response 반환
        return KeySetPaginatedResponse.<MyAbogResponse>builder()
                .content(pagedResults)
                .hasNext(hasNext)
                .lastSortValue(0L)  // 사용 안 함
                .lastId(newLastId)
                .totalElements(0)  // 사용 안 함
                .build();
    }

}
