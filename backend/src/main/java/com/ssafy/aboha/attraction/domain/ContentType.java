package com.ssafy.aboha.attraction.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contenttypes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ContentType {

    @Id
    @Column(name = "content_type_id")
    private Integer id;

    @Column(name = "content_type_name")
    private String name;

}
