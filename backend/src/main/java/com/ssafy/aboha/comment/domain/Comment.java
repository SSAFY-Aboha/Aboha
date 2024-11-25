package com.ssafy.aboha.comment.domain;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.common.BaseEntity;
import com.ssafy.aboha.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLRestriction("is_deleted = 0")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "abog_id", referencedColumnName = "id")
    private Abog abog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Column(name = "content")
    private String content;

    @Builder
    public Comment(User user, Abog abog, String content) {
        this.user = user;
        this.abog = abog;
        // TODO: 대댓글 적용 전
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
