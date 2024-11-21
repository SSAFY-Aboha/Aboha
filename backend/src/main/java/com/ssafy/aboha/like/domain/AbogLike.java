package com.ssafy.aboha.like.domain;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "abog_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AbogLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "abog_id", referencedColumnName = "id")
    private Abog abog;

    @Builder
    public AbogLike(User user, Abog abog) {
        this.user = user;
        this.abog = abog;
    }

}
