package com.brold.healthTalk.feed.likes.dto.response;

import java.time.LocalDateTime;

public class UserResponseDto {
    private Long userId;
    private String nickname;
    private LocalDateTime likedAt;

    public UserResponseDto(Long userId, String nickname, LocalDateTime likedAt) {
        this.userId = userId;
        this.nickname = nickname;
        this.likedAt = likedAt;
    }

    public Long getUserId() { return userId; }
    public String getNickname() { return nickname; }
    public LocalDateTime getLikedAt() { return likedAt; }
}
