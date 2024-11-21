package com.ssafy.aboha.abog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.domain.QAbog;
import com.ssafy.aboha.attraction.domain.QAttraction;
import com.ssafy.aboha.attraction.domain.QGugun;
import com.ssafy.aboha.attraction.domain.QSido;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

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
}
