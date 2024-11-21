package com.ssafy.aboha.attraction.repository;

import com.ssafy.aboha.attraction.domain.Gugun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GugunRepository extends JpaRepository<Gugun, Integer> {

    Optional<Gugun> findByCodeAndSido_Code(Integer code, Integer sidoCode);

    List<Gugun> findBySidoCode(Integer sidoCode);

    @Query("SELECT g FROM Gugun g WHERE g.sido.code = :sidoCode")
    List<Gugun> findGugunsBySido(@Param("sidoCode") Integer sidoCode);

}
