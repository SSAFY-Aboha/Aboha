package com.ssafy.aboha.attraction.repository;

import com.ssafy.aboha.attraction.domain.Sido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SidoRepository extends JpaRepository<Sido, Integer> {

    Optional<Sido> findByCode(Integer code);

}
