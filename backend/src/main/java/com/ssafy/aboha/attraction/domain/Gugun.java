package com.ssafy.aboha.attraction.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guguns")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Gugun {

    @Id
    @Column(name = "no")
    private Integer id;

    @Column(name = "gugun_code", unique = true)
    private Integer code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sido_code", referencedColumnName = "sido_code")
    private Sido sido;

    @Column(name = "gugun_name")
    private String name;

}
