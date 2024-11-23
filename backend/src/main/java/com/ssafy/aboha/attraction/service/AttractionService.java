package com.ssafy.aboha.attraction.service;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.attraction.domain.ContentType;
import com.ssafy.aboha.attraction.dto.request.AttractionReadPagedRequest;
import com.ssafy.aboha.attraction.dto.request.AttractionReadRequest;
import com.ssafy.aboha.attraction.dto.response.AttractionInfo;
import com.ssafy.aboha.attraction.dto.response.AttractionInfoWithLiked;
import com.ssafy.aboha.attraction.dto.response.AttractionResponse;
import com.ssafy.aboha.attraction.dto.response.AttractionSummary;
import com.ssafy.aboha.attraction.dto.response.GugunInfo;
import com.ssafy.aboha.attraction.dto.response.MyLikedAttractionResponse;
import com.ssafy.aboha.attraction.dto.response.MyReviewedAttractionResponse;
import com.ssafy.aboha.attraction.dto.response.SidoInfo;
import com.ssafy.aboha.attraction.repository.AttractionRepository;
import com.ssafy.aboha.attraction.repository.ContentTypeRepository;
import com.ssafy.aboha.attraction.repository.GugunRepository;
import com.ssafy.aboha.attraction.repository.SidoRepository;
import com.ssafy.aboha.common.dto.response.KeySetPaginatedResponse;
import com.ssafy.aboha.common.dto.response.PaginatedResponse;
import com.ssafy.aboha.common.exception.BadRequestException;
import com.ssafy.aboha.common.exception.NotFoundException;
import com.ssafy.aboha.like.repository.AttractionLikeRepository;
import com.ssafy.aboha.review.dto.response.ReviewResponse;
import com.ssafy.aboha.review.service.ReviewService;
import com.ssafy.aboha.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionService {

    private final AttractionRepository attractionRepository;
    private final SidoRepository sidoRepository;
    private final GugunRepository gugunRepository;
    private final ContentTypeRepository contentTypeRepository;
    private final ReviewService reviewService;
    private final UserRepository userRepository;
    private final AttractionLikeRepository likeRepository;

    /**
     * 관광지 목록 조회
     */
    public PaginatedResponse<AttractionInfo> getAttractionsByFilters(AttractionReadRequest request, Pageable pageable) {
        Integer sidoCode = request.sidoCode();
        Integer gugunCode = request.gugunCode();
        Integer contentTypeId = request.contentTypeId();

        validateSidoGugun(sidoCode, gugunCode);
        validateContentTypeId(contentTypeId);

        return attractionRepository.findAll(
                sidoCode,
                gugunCode,
                contentTypeId,
                request.keyword(),
                request.getSortOrDefault(),
                pageable
        );
    }

    // v2. 키셋 페이징 적용
    public KeySetPaginatedResponse<AttractionInfoWithLiked> getAttractions(
        AttractionReadPagedRequest request, Pageable pageable, Integer loginId) {

        // 1. 필터 조건 유효성 검사
        Integer sidoCode = request.sidoCode();
        Integer gugunCode = request.gugunCode();
        Integer contentTypeId = request.contentTypeId();
        validateSidoGugun(sidoCode, gugunCode);
        validateContentTypeId(contentTypeId);

        // 2. 좋아요한 Attraction ID 리스트 조회
        List<Integer> likedAttractionIds = (loginId != null)
            ? likeRepository.findAttractionIdByUserId(loginId)
            : List.of();

        // 3. AttractionInfo 리스트 조회
        KeySetPaginatedResponse<AttractionInfo> attractions = attractionRepository.findAlls(
            sidoCode,
            gugunCode,
            contentTypeId,
            request.keyword(),
            request.getSortOrDefault(),
            request.lastSortValue(),
            request.lastId(),
            pageable
        );

        // 4. AttractionInfo를 AttractionResponseWithLiked로 변환
        List<AttractionInfoWithLiked> updatedContent = attractions.content().stream()
            .map(attractionInfo -> AttractionInfoWithLiked.of(
                attractionInfo,
                likedAttractionIds.contains(attractionInfo.id()) // 좋아요 여부
            ))
            .toList();

        // 5. 새로운 KeySetPaginatedResponse 반환
        return KeySetPaginatedResponse.<AttractionInfoWithLiked>builder()
            .content(updatedContent)
            .hasNext(attractions.hasNext())
            .lastSortValue(attractions.lastSortValue())
            .lastId(attractions.lastId())
            .totalElements(attractions.totalElements())
            .build();
    }

    /**
     * 관광지 상세 조회
     */
    @Transactional
    public AttractionResponse getAttraction(Integer id, Integer loginId) {
        Attraction attraction = attractionRepository.findByAttractionId(id)
            .orElseThrow(() -> new NotFoundException("관광지가 존재하지 않습니다."));

        // 조회수 증가
        attraction.increaseViewCount();

        // 좋아요 여부 확인
        boolean isLiked = (loginId != null) && likeRepository.existsByUserIdAndAttractionId(loginId, id);

        // 리뷰 목록 조회
        List<ReviewResponse> reviews = reviewService.getReviews(attraction);

        return AttractionResponse.of(attraction, reviews, isLiked);
    }

    /**
     * 시도 조회
     */
    public List<SidoInfo> getSidos() {
        return sidoRepository.findAll().stream()
            .map(SidoInfo::from)
            .toList();
    }

    /**
     * 구군 조회
     */
    public List<GugunInfo> getGuguns(Integer sidoCode) {
        validateSidoCode(sidoCode);
        return gugunRepository.findBySidoCode(sidoCode).stream()
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

    /**
     * 사용자가 좋아요한 관광지 목록 조회
     */
    public KeySetPaginatedResponse<MyLikedAttractionResponse> getUserLikedAttractions(Integer userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("로그인한 사용자가 존재하지 않습니다.");
        }
        return attractionRepository.findByUserLiked(userId, pageable);
    }

    /**
     * 사용자가 리뷰 남긴 관광지 목록 조회
     */
    public KeySetPaginatedResponse<MyReviewedAttractionResponse> getUserReviewedAttractions(Integer userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("로그인한 사용자가 존재하지 않습니다.");
        }
        return attractionRepository.findByUserReviewed(userId, pageable);
    }

    private void validateSidoGugun(Integer sidoCode, Integer gugunCode) {
        if (sidoCode == null && gugunCode == null) {
            return;
        }
        if (gugunCode != null && sidoCode == null) {
            throw new BadRequestException("시도 코드 없이 구군 코드만 존재할 수 없습니다.");
        }
        validateSidoCode(sidoCode);
        validateGugunCodeWithSido(gugunCode, sidoCode);
    }

    private void validateSidoCode(Integer sidoCode) {
        sidoRepository.findByCode(sidoCode)
            .orElseThrow(() -> new NotFoundException("시도 코드가 존재하지 않습니다."));
    }

    private void validateGugunCodeWithSido(Integer gugunCode, Integer sidoCode) {
        gugunRepository.findByCodeAndSido_Code(gugunCode, sidoCode)
            .orElseThrow(() -> new NotFoundException("구군 코드가 존재하지 않습니다."));
    }

    private void validateContentTypeId(Integer contentTypeId) {
        if (contentTypeId == null) {
            return;
        }
        contentTypeRepository.findById(contentTypeId)
            .orElseThrow(() -> new NotFoundException("관광지 유형 식별자가 존재하지 않습니다."));
    }
}
