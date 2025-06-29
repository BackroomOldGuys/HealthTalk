package com.brold.healthTalk.user.follow.domain;

import com.brold.healthTalk.user.common.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_follows") // 테이블 이름 수정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFollow {

    @EmbeddedId // 복합 키를 사용함을 명시
    private UserFollowId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followerId") // UserFollowId의 followerId 필드에 매핑
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followingId") // UserFollowId의 followingId 필드에 매핑
    @JoinColumn(name = "following_id")
    private User following;

    // createdAt 필드가 스키마에 있으므로 추가할 수 있습니다.
    // private LocalDateTime createdAt;
}