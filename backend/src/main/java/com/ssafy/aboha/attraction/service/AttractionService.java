package com.ssafy.aboha.attraction.service;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.dto.request.AttractionSearchRequest;
import com.ssafy.aboha.attraction.dto.response.AttractionInfo;
import com.ssafy.aboha.attraction.repository.AttractionRepository;
import com.ssafy.aboha.attraction.repository.ContentTypeRepository;
import com.ssafy.aboha.attraction.repository.GugunRepository;
import com.ssafy.aboha.attraction.repository.SidoRepository;
import com.ssafy.aboha.common.exception.BadRequestException;
import com.ssafy.aboha.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionService {

    private final AttractionRepository attractionRepository;
    private final SidoRepository sidoRepository;
    private final GugunRepository gugunRepository;
    private final ContentTypeRepository contentTypeRepository;

    /**
     * 관광지 목록 조회
     */
    // TODO: Pagenation 적용 전
    public List<AttractionInfo> getAttractionsByFilters(AttractionSearchRequest request) {
        Integer sidoCode = request.sidoCode();
        Integer gugunCode = request.gugunCode();
        Integer contentTypeId = request.contentTypeId();

        // sidoCode 없이 gugunCode가 제공되는 경우 예외 발생
        if (gugunCode != null && sidoCode == null) {
            throw new BadRequestException("시도 코드 없이 구군 코드만 존재할 수 없습니다.");
        }

        validateSidoCode(sidoCode);
        validateGugunCodeWithSido(gugunCode, sidoCode);
        validateContentTypeId(contentTypeId);

        List<Attraction> attractions = attractionRepository.findByFilters(sidoCode, gugunCode, contentTypeId);

        return attractions.stream()
                .map(AttractionInfo::from)
                .toList();
    }

    // 시도 코드 유효성 검사
    private void validateSidoCode(Integer sidoCode) {
        sidoRepository.findByCode(sidoCode)
                .orElseThrow(() -> new NotFoundException("시도 코드가 존재하지 않습니다."));
    }

    // 구군 코드 유효성 검사
    private void validateGugunCodeWithSido(Integer gugunCode, Integer sidoCode) {
        gugunRepository.findByCodeAndSido_Code(gugunCode, sidoCode)
                .orElseThrow(() -> new NotFoundException("구군 코드가 존재하지 않습니다."));
    }

    // 관광지 유형 식별자 유효성 검사
    private void validateContentTypeId(Integer contentTypeId) {
        contentTypeRepository.findById(contentTypeId)
                .orElseThrow(() -> new NotFoundException("관광지 유형 식별자가 존재하지 않습니다."));
    }

}
