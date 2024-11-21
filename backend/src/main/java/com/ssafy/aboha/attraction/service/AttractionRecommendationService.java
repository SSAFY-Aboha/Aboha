package com.ssafy.aboha.attraction.service;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.domain.Gugun;
import com.ssafy.aboha.attraction.dto.request.AttractionRecommendRequest;
import com.ssafy.aboha.attraction.dto.response.AttractionInfo;
import com.ssafy.aboha.attraction.dto.response.GugunInfo;
import com.ssafy.aboha.attraction.repository.AttractionRepository;
import com.ssafy.aboha.attraction.repository.GugunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionRecommendationService {

    private final AttractionRepository attractionRepository;
    private final GugunRepository gugunRepository;
    private final OpenAiAttractionService openAiAttractionService;

    /**
     * 추천 관광지를 가져옵니다.
     *
     * @param request 추천 요청 데이터
     * @return 추천된 관광지 리스트
     */
    public List<AttractionInfo> getRecommendedAttractions(AttractionRecommendRequest request) {
        // Step 1: 기본 필터 조건으로 관광지 조회
        List<Attraction> attractions = attractionRepository.findByFilters(
                request.sidoCode(),
                List.of(request.gugunCode()), // 초기에는 단일 구군만 필터링
                request.contentTypeId()
        );

        // Step 2: 분위기에 적합한 관광지를 OpenAI를 통해 추천받음
        List<AttractionInfo> recommendations = new ArrayList<>(openAiAttractionService.getSimilarAttractions(request.atmosphere(), attractions));

        // Step 3: 추천 결과가 부족하면 인접 구군에서 관광지를 추가
        if (recommendations.size() < 6) {
            // 시도에 속한 모든 구군 리스트 가져오기
            List<Gugun> allGuguns = gugunRepository.findGugunsBySido(request.sidoCode());

            // 현재 구군 정보
            Gugun currentGugun = allGuguns.stream()
                    .filter(gugun -> gugun.getCode().equals(request.gugunCode()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("현재 구군 정보가 유효하지 않습니다."));

            // 구군 정보를 GugunInfo 형태로 변환
            List<GugunInfo> allGugunInfos = allGuguns.stream()
                    .map(GugunInfo::from)
                    .toList();

            GugunInfo currentGugunInfo = GugunInfo.from(currentGugun);

            // 가까운 구군 3개를 OpenAI로 결정
            List<GugunInfo> nearbyGuguns = openAiAttractionService.getNearbyGugunsFromOpenAi(
                    request.sidoCode(),
                    currentGugunInfo,
                    allGugunInfos
            );

            // 인접 구군 포함하여 관광지 조회
            List<Attraction> additionalAttractions = attractionRepository.findByFilters(
                    request.sidoCode(),
                    nearbyGuguns.stream().map(GugunInfo::code).toList(),
                    request.contentTypeId()
            );

            // 추가된 관광지로 부족한 추천 수를 채움
            List<AttractionInfo> additionalRecommendations = openAiAttractionService.getSimilarAttractions(request.atmosphere(), additionalAttractions);

            for (AttractionInfo attractionInfo : additionalRecommendations) {
                if (!recommendations.contains(attractionInfo)) {
                    recommendations.add(attractionInfo); // Mutable List에 추가
                }
                if (recommendations.size() >= 6) break;
            }
        }

        return recommendations;
    }

}



