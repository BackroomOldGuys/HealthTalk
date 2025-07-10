package com.brold.healthTalk.user.common.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "body_discomforts")
@Getter
@NoArgsConstructor // JPA는 기본 생성자가 필요합니다.
public class BodyDiscomfort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;
}