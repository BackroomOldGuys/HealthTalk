package com.brold.healthTalk.feed.tag.domain;

import java.io.Serializable;
import java.util.Objects;

public class FeedTagId implements Serializable {
    private Long    feedId;
    private Integer tagId;

    public FeedTagId() {}

    public FeedTagId(Long feedId, Integer tagId) {
        this.feedId = feedId;
        this.tagId  = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeedTagId)) return false;
        FeedTagId that = (FeedTagId) o;
        return Objects.equals(feedId, that.feedId)
                && Objects.equals(tagId,  that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedId, tagId);
    }
}
