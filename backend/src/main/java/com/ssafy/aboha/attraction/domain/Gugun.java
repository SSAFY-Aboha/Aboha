package com.ssafy.aboha.attraction.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guguns", uniqueConstraints = @UniqueConstraint(columnNames = {"sido_code", "gugun_code"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Gugun {

    @Id
    @Column(name = "no")
    private Integer id;

    @Column(name = "gugun_code")
    private Integer code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sido_code", referencedColumnName = "sido_code")
    private Sido sido;

    @Column(name = "gugun_name")
    private String name;
}
