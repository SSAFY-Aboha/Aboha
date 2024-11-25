package com.ssafy.aboha.abog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "abog_tags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AbogTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "abog_id", referencedColumnName = "id")
    private Abog abog;

    @Column(name = "name")
    private String name;

    @Builder
    public AbogTag(Abog abog, String name) {
        this.abog = abog;
        this.name = name;
    }
}
