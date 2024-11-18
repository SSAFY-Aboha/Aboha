package com.ssafy.aboha.attraction.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.domain.QAttraction;
import com.ssafy.aboha.attraction.domain.QContentType;
import com.ssafy.aboha.attraction.domain.QGugun;
import com.ssafy.aboha.attraction.domain.QSido;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class AttractionCustomRepositoryImpl implements AttractionCustomRepository {

    private final JPAQueryFactory queryFactory;

    public AttractionCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Attraction> findByFilters(Integer sidoCode, Integer gugunCode, Integer contentTypeId) {
        QAttraction qAttraction = QAttraction.attraction;
        QSido qSido = QSido.sido;
        QGugun qGugun = QGugun.gugun;
        QContentType qContenttype = QContentType.contentType;

        BooleanBuilder builder = new BooleanBuilder();

        if (sidoCode != null) {
            builder.and(qSido.code.eq(sidoCode));
            builder.and(qGugun.sido.code.eq(sidoCode));
        }
        if (gugunCode != null) {
            builder.and(qGugun.code.eq(gugunCode));
        }
        if (contentTypeId != null) {
            builder.and(qContenttype.id.eq(contentTypeId));
        }

        return queryFactory.selectFrom(qAttraction)
                .leftJoin(qAttraction.sido, qSido).fetchJoin()
                .leftJoin(qAttraction.gugun, qGugun).fetchJoin()
                .leftJoin(qAttraction.contentType, qContenttype).fetchJoin()
                .where(builder)
                .fetch()
                .stream()
                .distinct()
                .toList();

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

}
