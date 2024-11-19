
package com.ssafy.aboha.abog.domain;

import com.ssafy.aboha.attraction.domain.Attraction;
import com.ssafy.aboha.common.BaseEntity;
import com.ssafy.aboha.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "abogs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Abog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id", referencedColumnName = "no")
    private Attraction attraction;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "comment_count")
    private Long commentCount;

    // TODO: 해시태그 적용 전

    @Builder
    public Abog(User user, Attraction attraction, String title, String content) {
        this.user = user;
        this.attraction = attraction;
        this.title = title;
        this.content = content;
        this.likeCount = 0L;
        this.commentCount = 0L;
    }

}
