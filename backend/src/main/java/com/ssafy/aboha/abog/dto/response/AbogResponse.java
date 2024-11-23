package com.ssafy.aboha.abog.dto.response;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.attraction.dto.response.AttractionSummary;
import com.ssafy.aboha.user.dto.response.UserSummary;
import java.util.List;
import lombok.Builder;

@Builder
public record AbogResponse(
        // 사용자
        UserSummary user,
        // 관광지
        AttractionSummary attraction,
        // 아보그
        AbogSummary abog,
        // 좋아요 여부
        Boolean isLiked
) {

    public static AbogResponse of(Abog abog, List<String> imageUrls, Boolean isLiked) {
        UserSummary userSummary = UserSummary.from(abog.getUser());
        AttractionSummary attractionSummary = AttractionSummary.from(abog.getAttraction());
        AbogSummary abogSummary = AbogSummary.of(abog, imageUrls);

        return AbogResponse.builder()
            .user(userSummary)
            .attraction(attractionSummary)
            .abog(abogSummary)
            .isLiked(isLiked)
            .build();
    }

}
