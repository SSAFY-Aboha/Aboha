package com.ssafy.aboha.user.domain;

import com.ssafy.aboha.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLRestriction("is_deleted = 0")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_image_url")
    private String profileImageUrl;


    @Builder
    public User(String nickname, String email, String password, String profileImageUrl) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
    }

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateProfileImageUrl(String newProfileImageUrl) {
        this.profileImageUrl = newProfileImageUrl;
    }

}
