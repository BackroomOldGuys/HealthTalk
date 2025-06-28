package com.brold.healthTalk.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY) // 2. User와 1:1 관계를 설정합니다.
    @MapsId // 3. User의 ID를 RefreshToken의 ID(@Id로 지정된 필드)에 직접 매핑합니다.
    @JoinColumn(name = "user_id")
    private User user;

    private String refreshToken;
    private LocalDateTime expiresAt;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
