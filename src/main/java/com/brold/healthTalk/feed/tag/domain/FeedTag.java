package com.brold.healthTalk.feed.tag.domain;

import com.brold.healthTalk.feed.content.domain.Feed;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "feed_tags")
@IdClass(FeedTagId.class)
public class FeedTag {
    @Id @Column(name = "feed_id") private Long feedId;
    @Id @Column(name = "tag_id")  private Integer tagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("feedId")
    @JoinColumn(name = "feed_id", insertable = false, updatable = false)
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private Tag tag;

    protected FeedTag() {}

    // 변경: feed, tag 객체를 받아야 합니다!
    public FeedTag(Feed feed, Tag tag) {
        this.feed = feed;
        this.tag  = tag;
    }
}