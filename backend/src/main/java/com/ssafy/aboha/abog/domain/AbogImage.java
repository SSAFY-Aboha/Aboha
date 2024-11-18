package com.ssafy.aboha.abog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "abog_images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AbogImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "abog_id", referencedColumnName = "id")
    private Abog abog;

    @Column(name = "image_url")
    private String imageUrl;

    @Builder
    public AbogImage(Abog abog, String imageUrl) {
        this.abog = abog;
        this.imageUrl = imageUrl;
    }

}
