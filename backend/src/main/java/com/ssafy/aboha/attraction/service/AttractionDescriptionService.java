package com.ssafy.aboha.attraction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttractionDescriptionService {

    private final OpenAiChatModel openAiChatModel;

    public String generateDescription(String title, String address) {
        String prompt = String.format(
            "%s에 위치한 관광지 %s에 대해 간단한 설명을 2줄 요약으로 작성해 주세요.",
            address, title
        );
        return openAiChatModel.call(prompt);
    }
}
