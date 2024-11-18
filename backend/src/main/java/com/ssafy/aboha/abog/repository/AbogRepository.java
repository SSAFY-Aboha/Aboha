package com.ssafy.aboha.abog.repository;

import com.ssafy.aboha.abog.domain.Abog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbogRepository extends JpaRepository<Abog, Integer>, AbogCustomRepository {

}
