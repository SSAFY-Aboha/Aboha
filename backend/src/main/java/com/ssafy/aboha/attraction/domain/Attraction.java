package com.ssafy.aboha.attraction.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attractions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Attraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Integer id;

    @Column(name = "content_id")
    private Integer contentId;

    // Sido 관계 설정 (insertable=false, updatable=false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_code", referencedColumnName = "sido_code", insertable = false, updatable = false)
    private Sido sido;

    // Gugun 관계 설정 (복합 외래 키, insertable=false, updatable=false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "area_code", referencedColumnName = "sido_code", insertable = false, updatable = false),
        @JoinColumn(name = "si_gun_gu_code", referencedColumnName = "gugun_code", insertable = false, updatable = false)
    })
    private Gugun gugun;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_type_id", referencedColumnName = "content_type_id")
    private ContentType contentType;

    @Column(name = "title")
    private String title;

    @Column(name = "first_image1")
    private String firstImage1;

    @Column(name = "first_image2")
    private String firstImage2;

    @Column(name = "map_level")
    private Integer mapLevel;

    @Column(name = "latitude", precision = 20, scale = 17)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 20, scale = 17)
    private BigDecimal longitude;

    @Column(name = "tel")
    private String tel;

    @Column(name = "addr1")
    private String addr1;

    @Column(name = "addr2")
    private String addr2;

    @Column(name = "homepage")
    private String homepage;

    @Column(name = "overview", length = 10000)
    private String overview;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "review_count")
    private Long reviewCount;

    @Column(name = "rating_sum", precision = 5, scale = 1)
    private BigDecimal ratingSum;

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    // 리뷰 추가
    public void addReview(BigDecimal rating) {
        this.ratingSum = this.ratingSum.add(rating);
        this.reviewCount += 1;
    }

    // 리뷰 수정
    public void updateReview(BigDecimal oldRating, BigDecimal newRating) {
        this.ratingSum = this.ratingSum.subtract(oldRating).add(newRating);
    }

    // 리뷰 삭제
    public void deleteReview(BigDecimal rating) {
        this.ratingSum = this.ratingSum.subtract(rating);
        this.reviewCount -= 1;
    }

    // 평균 평점 계산
    public BigDecimal calculateAverageRating() {
        return this.reviewCount == 0
                ? BigDecimal.ZERO
                : this.ratingSum
                .divide(BigDecimal.valueOf(this.reviewCount), 1, RoundingMode.HALF_UP); // 소수점 첫째자리 반올림
    }

}
