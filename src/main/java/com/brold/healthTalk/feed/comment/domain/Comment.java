package com.brold.healthTalk.feed.comment.domain;

import com.brold.healthTalk.feed.content.domain.Feed;
import com.brold.healthTalk.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "feed_comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 피드에 달린 댓글인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    // 댓글 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 댓글 내용
    @Column(name = "comment_text", nullable = false)
    private String commentText;

    // 작성 시각
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    protected Comment() {
        // JPA 리플렉션용
    }

    public Comment(Feed feed, User user, String commentText) {
        this.feed = feed;
        this.user = user;
        this.commentText = commentText;
    }

}
