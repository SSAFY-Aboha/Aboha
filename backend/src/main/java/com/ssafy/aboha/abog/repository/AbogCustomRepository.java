package com.ssafy.aboha.abog.repository;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.dto.response.MyAbogResponse;
import com.ssafy.aboha.common.dto.response.KeySetPaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AbogCustomRepository {

    // 아보그 조회
    Optional<Abog> findByAbogId(Integer id);

    // 사용자의 아보그 목록 조회
    KeySetPaginatedResponse<MyAbogResponse> findByUserAbog(Integer userId, Pageable pageable);
}
