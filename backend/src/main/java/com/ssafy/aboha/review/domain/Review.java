package com.ssafy.aboha.review.domain;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.common.BaseEntity;
import com.ssafy.aboha.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id", referencedColumnName = "no")
    private Attraction attraction;

    @Column(name = "rating", nullable = false)
    @Min(1) // 최소값 1
    @Max(5) // 최대값 5
    private Integer rating;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Builder
    public Review(User user, Attraction attraction, Integer rating, String content) {
        this.user = user;
        this.attraction = attraction;
        this.rating = rating;
        this.content = content;
    }

}
