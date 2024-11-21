package com.ssafy.aboha.attraction.service;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.dto.response.AttractionInfo;
import com.ssafy.aboha.attraction.dto.response.GugunInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiAttractionService {

    private final OpenAiChatModel openAiChatModel;

    /**
     * OpenAI를 사용하여 분위기에 적합한 관광지를 추천합니다.
     *
     * @param atmospheres 분위기 리스트
     * @param attractions 관광지 리스트
     * @return 추천된 관광지 리스트
     */
    public List<AttractionInfo> getSimilarAttractions(List<String> atmospheres, List<Attraction> attractions) {
        // 관광지 목록이 6개 이하인 경우 OpenAI 호출 생략
        if (attractions.size() <= 6) {
            return attractions.stream()
                    .map(AttractionInfo::from)
                    .toList();
        }

        // OpenAI 호출
        String prompt = createPrompt(atmospheres, attractions);
        String result = openAiChatModel.call(prompt);

        // OpenAI 응답 파싱
        return parseResponse(result, attractions);
    }

    /**
     * OpenAI 프롬프트 생성
     */
    private String createPrompt(List<String> atmospheres, List<Attraction> attractions) {
        StringBuilder sb = new StringBuilder("다음 관광지 중 아래 분위기에 가장 적합한 6개의 숫자만 반환하세요.\n\n");
        sb.append("분위기: ").append(String.join(", ", atmospheres)).append("\n\n");
        sb.append("관광지 목록:\n");

        for (int i = 0; i < attractions.size(); i++) {
            Attraction attraction = attractions.get(i);
            sb.append(i + 1).append(". ")
                    .append(attraction.getTitle()).append(" - ")
                    .append(attraction.getOverview()).append("\n");
        }

        sb.append("\n응답 형식 예시: [1, 3, 5, 7, 9, 12]");
        return sb.toString();
    }

    /**
     * OpenAI 응답 파싱
     */
    private List<AttractionInfo> parseResponse(String response, List<Attraction> attractions) {
        // OpenAI 응답 형식 검증
        if (!response.contains("[") || !response.contains("]")) {
            throw new RuntimeException("OpenAI 응답 형식이 예상과 다릅니다. 응답: " + response);
        }

        // 대괄호 제거 및 숫자 파싱
        response = response.replaceAll("[\\[\\]]", ""); // 대괄호 제거
        String[] indices = response.split(", ");
        List<AttractionInfo> recommendedAttractions = new ArrayList<>();

        for (String index : indices) {
            try {
                int idx = Integer.parseInt(index.trim()) - 1;
                if (idx >= 0 && idx < attractions.size()) {
                    recommendedAttractions.add(AttractionInfo.from(attractions.get(idx)));
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("OpenAI 응답에 올바르지 않은 숫자가 포함되어 있습니다: " + index, e);
            }
        }

        return recommendedAttractions;
    }

    /**
     * OpenAI를 사용해 현재 구군과 가까운 구군을 반환합니다.
     *
     * @param sidoCode 시도 코드
     * @param currentGugun 현재 구군 정보
     * @param allGuguns 시도 내 모든 구군 정보
     * @return 가까운 구군 리스트
     */
    public List<GugunInfo> getNearbyGugunsFromOpenAi(Integer sidoCode, GugunInfo currentGugun, List<GugunInfo> allGuguns) {
        String prompt = createNearbyGugunsPrompt(sidoCode, currentGugun, allGuguns);

        // OpenAI 호출
        String response = openAiChatModel.call(prompt);

        // 응답 파싱
        return parseNearbyGugunResponse(response, allGuguns);
    }

    /**
     * OpenAI 프롬프트 생성
     */
    private String createNearbyGugunsPrompt(Integer sidoCode, GugunInfo currentGugun, List<GugunInfo> allGuguns) {
        StringBuilder sb = new StringBuilder("다음은 시도 코드와 구군 코드에 대한 정보입니다.\n\n");
        sb.append("현재 시도 코드: ").append(sidoCode).append("\n");
        sb.append("현재 구군: ").append(currentGugun.name()).append("\n");
        sb.append("시도 내 구군 목록:\n");

        for (GugunInfo gugun : allGuguns) {
            sb.append("- ").append(gugun.name()).append(" (코드: ").append(gugun.code()).append(")\n");
        }

        sb.append("\n")
                .append("현재 구군과 가까운 구군 3개를 골라주세요. 응답 형식은 아래와 같습니다:\n")
                .append("예: [2, 3, 4]");
        return sb.toString();
    }

    /**
     * OpenAI 응답 파싱
     */
    private List<GugunInfo> parseNearbyGugunResponse(String response, List<GugunInfo> allGugunInfos) {
        // OpenAI 응답 형식 검증
        if (!response.contains("[") || !response.contains("]")) {
            throw new RuntimeException("OpenAI 응답 형식이 예상과 다릅니다. 응답: " + response);
        }

        // 대괄호와 불필요한 문구 제거
        response = response.substring(response.indexOf("[") + 1, response.indexOf("]")).trim(); // [] 내부 추출
        String[] codes = response.split(",");

        // 구군 코드 매핑
        List<Integer> gugunCodes = new ArrayList<>();
        for (String code : codes) {
            try {
                String trimmedCode = code.trim();
                if (!trimmedCode.isEmpty()) { // 공백 문자열 필터링
                    gugunCodes.add(Integer.parseInt(trimmedCode));
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("OpenAI 응답에 올바르지 않은 숫자가 포함되어 있습니다: " + code, e);
            }
        }

        // 구군 코드에 매칭되는 GugunInfo 반환
        return allGugunInfos.stream()
                .filter(gugun -> gugunCodes.contains(gugun.code()))
                .toList();
    }

}

