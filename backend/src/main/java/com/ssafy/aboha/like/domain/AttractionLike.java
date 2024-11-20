package com.ssafy.aboha.like.domain;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attraction_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AttractionLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id", referencedColumnName = "no")
    private Attraction attraction;

    @Builder
    public AttractionLike(User user, Attraction attraction) {
        this.user = user;
        this.attraction = attraction;
    }

}
