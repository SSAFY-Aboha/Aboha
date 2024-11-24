package com.ssafy.aboha.abog.repository;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.dto.response.MyAbogResponse;
import com.ssafy.aboha.common.dto.response.KeySetPaginatedResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface AbogCustomRepository {

    // 아보그 조회
    Optional<Abog> findByAbogId(Integer id);

    // 아보그 목록
    List<Abog> findAll();

    // 사용자의 아보그 목록 조회
    KeySetPaginatedResponse<MyAbogResponse> findByUserAbog(Integer userId, Pageable pageable);

    Long countUserAbog(Integer userId);
}
