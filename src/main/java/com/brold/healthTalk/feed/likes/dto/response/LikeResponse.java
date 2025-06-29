package com.brold.healthTalk.feed.likes.dto.response;

public class LikeResponse {
    private Long feedId;
    private long totalLikes;

    public LikeResponse(Long feedId, long totalLikes) {
        this.feedId = feedId;
        this.totalLikes = totalLikes;
    }

    public Long getFeedId() { return feedId; }
    public long getTotalLikes() { return totalLikes; }
}
