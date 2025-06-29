package com.brold.healthTalk.feed.likes.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class LikeId implements Serializable {
    private Long userId;
    private Long feedId;

    public LikeId() {}

    public LikeId(Long userId, Long feedId) {
        this.userId = userId;
        this.feedId = feedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikeId)) return false;
        LikeId that = (LikeId) o;
        return Objects.equals(userId, that.userId)
                && Objects.equals(feedId, that.feedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, feedId);
    }

    // getters/setters
}
