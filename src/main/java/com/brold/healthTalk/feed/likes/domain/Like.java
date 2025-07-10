package com.brold.healthTalk.feed.likes.domain;

import com.brold.healthTalk.user.common.domain.User;
import com.brold.healthTalk.feed.content.domain.Feed;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "feed_likes")
@IdClass(LikeId.class)
public class Like {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "feed_id")
    private Long feedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", insertable = false, updatable = false)
    private Feed feed;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    protected Like() {}

    public Like(Long userId, Long feedId) {
        this.userId = userId;
        this.feedId = feedId;
    }

    // getters
}
