package com.brold.healthTalk.feed.likes.dto.request;

public class LikeRequest {
    private Long userId;
    public LikeRequest() {}
    public LikeRequest(Long userId) { this.userId = userId; }
    public Long getUserId() { return userId; }
}
