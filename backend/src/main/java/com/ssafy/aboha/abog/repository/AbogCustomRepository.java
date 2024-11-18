package com.ssafy.aboha.abog.repository;

import com.ssafy.aboha.abog.domain.Abog;

import java.util.Optional;

public interface AbogCustomRepository {

    Optional<Abog> findByAbogId(Integer id);
}
