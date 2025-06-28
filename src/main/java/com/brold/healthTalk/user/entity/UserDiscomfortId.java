package com.brold.healthTalk.user.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDiscomfortId implements Serializable {
    private Long userId;
    private Integer discomfortId;
}