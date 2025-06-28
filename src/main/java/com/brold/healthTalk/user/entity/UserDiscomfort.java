package com.brold.healthTalk.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_discomforts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDiscomfort {

    @EmbeddedId
    private UserDiscomfortId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    // BodyDiscomfort 엔티티가 있다면 아래처럼 매핑할 수 있습니다.
    // @ManyToOne(fetch = FetchType.LAZY)
    // @MapsId("discomfortId")
    // @JoinColumn(name = "discomfort_id")
    // private BodyDiscomfort bodyDiscomfort;
}