package com.ssafy.aboha.abog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.domain.QAbog;
import com.ssafy.aboha.attraction.domain.QAttraction;
import com.ssafy.aboha.attraction.domain.QGugun;
import jakarta.persistence.EntityManager;
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

        return Optional.ofNullable(
                queryFactory
                        .selectDistinct(qAbog)
                        .from(qAbog)
                        .leftJoin(qAbog.attraction, qAttraction).fetchJoin()
                        .leftJoin(qAttraction.gugun, qGugun).fetchJoin()
                        .where(qAbog.id.eq(id))
                        .fetchOne()
        );
    }

    @Override
    public List<Abog> findAll() {
        QAbog qAbog = QAbog.abog;
        QAttraction qAttraction = QAttraction.attraction;
        QGugun qGugun = QGugun.gugun;

       return queryFactory
               .selectDistinct(qAbog)
               .from(qAbog)
               .leftJoin(qAbog.attraction, qAttraction).fetchJoin()
               .leftJoin(qAttraction.gugun, qGugun).fetchJoin()
               .fetch()
               .stream()
               .distinct()
               .toList();
    }
}
