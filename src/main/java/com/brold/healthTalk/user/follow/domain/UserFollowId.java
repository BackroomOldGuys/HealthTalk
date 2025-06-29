package com.brold.healthTalk.user.follow.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserFollowId implements Serializable {
    private Long followerId;
    private Long followingId;
}