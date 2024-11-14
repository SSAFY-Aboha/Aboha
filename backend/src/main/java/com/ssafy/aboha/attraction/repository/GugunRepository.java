package com.ssafy.aboha.attraction.repository;

import com.ssafy.aboha.attraction.domain.Gugun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GugunRepository extends JpaRepository<Gugun, Integer> {

    Optional<Gugun> findByCodeAndSido_Code(Integer code, Integer sidoCode);

}
