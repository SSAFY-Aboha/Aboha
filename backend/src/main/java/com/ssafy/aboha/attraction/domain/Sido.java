package com.ssafy.aboha.attraction.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sidos")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Sido {

    @Id
    @Column(name = "no")
    private Integer id;

    @Column(name = "sido_code", unique = true)
    private Integer code;

    @Column(name = "sido_name")
    private String name;

}
