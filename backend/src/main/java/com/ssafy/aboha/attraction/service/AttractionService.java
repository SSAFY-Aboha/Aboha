package com.ssafy.aboha.attraction.service;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.domain.ContentType;
import com.ssafy.aboha.attraction.domain.Gugun;
import com.ssafy.aboha.attraction.domain.Sido;
import com.ssafy.aboha.attraction.dto.request.AttractionReadRequest;
import com.ssafy.aboha.attraction.dto.response.*;
import com.ssafy.aboha.attraction.repository.AttractionRepository;
import com.ssafy.aboha.attraction.repository.ContentTypeRepository;
import com.ssafy.aboha.attraction.repository.GugunRepository;
import com.ssafy.aboha.attraction.repository.SidoRepository;
import com.ssafy.aboha.common.dto.response.PaginatedResponse;
import com.ssafy.aboha.common.exception.BadRequestException;
import com.ssafy.aboha.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    private final AttractionDescriptionService attractionDescriptionService;

    /**
     * 관광지 목록 조회
     */
    public PaginatedResponse<AttractionInfo> getAttractionsByFilters(AttractionReadRequest request, Pageable pageable) {
        Integer sidoCode = request.sidoCode();
        Integer gugunCode = request.gugunCode();
        Integer contentTypeId = request.contentTypeId();

        validateSidoGugun(sidoCode, gugunCode);
        validateContentTypeId(contentTypeId);

        return attractionRepository.findByFilters(
                sidoCode,
                gugunCode,
                contentTypeId,
                request.keyword(),
                request.getSortOrDefault(),
                pageable
        );
    }

    /**
     * 관광지 상세 조회
     */
    @Transactional
    public AttractionResponse getAttraction(Integer id) {
        Attraction attraction = attractionRepository.findByAttractionId(id)
            .orElseThrow(() -> new NotFoundException("관광지가 존재하지 않습니다."));

        // 조회수 증가
        attraction.increaseViewCount();

        // Spring AI 활용한 설명 생성
        String description = attractionDescriptionService.generateDescription(
            attraction.getTitle(),
            attraction.getAddr1() + attraction.getAddr2()
        );

        return AttractionResponse.of(attraction, description);
    }

    /**
     * 시도 조회
     */
    public List<SidoInfo> getSidos() {
        List<Sido> sidos = sidoRepository.findAll();

        return sidos.stream()
                .map(SidoInfo::from)
                .toList();
    }

    /**
     * 구군 조회
     */
    public List<GugunInfo> getGuguns(Integer sidoCode) {
        // 시도 코드 유효성 검사
        validateSidoCode(sidoCode);

        List<Gugun> guguns = gugunRepository.findBySidoCode(sidoCode);

        return guguns.stream()
                .map(GugunInfo::from)
                .toList();
    }

    /**
     * 관광지 유형 조회
     */
    public List<ContentType> getContentTypes() {
        return contentTypeRepository.findAll();
    }

    /**
     * 관광지 이름 검색
     */
    public List<AttractionSummary> getAttractionsByTitle(String keyword) {
        return attractionRepository.findByTitle(keyword);
    }

    private void validateSidoGugun(Integer sidoCode, Integer gugunCode) {
        if(sidoCode == null && gugunCode == null) {
            return;
        }

        // sidoCode 없이 gugunCode가 제공되는 경우 예외 발생
        if (gugunCode != null && sidoCode == null) {
            throw new BadRequestException("시도 코드 없이 구군 코드만 존재할 수 없습니다.");
        }

        validateSidoCode(sidoCode);
        validateGugunCodeWithSido(gugunCode, sidoCode);
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
        if(contentTypeId == null) {
            return;
        }
        contentTypeRepository.findById(contentTypeId)
                .orElseThrow(() -> new NotFoundException("관광지 유형 식별자가 존재하지 않습니다."));
    }

}
