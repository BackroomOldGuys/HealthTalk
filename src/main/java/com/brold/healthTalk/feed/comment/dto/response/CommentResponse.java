package com.brold.healthTalk.feed.comment.dto.response;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private Long userId;
    private String nickname;
    private String profileImageUrl;   // 추가된 필드
    private String commentText;
    private LocalDateTime createdAt;

    public CommentResponse(
            Long id,
            Long userId,
            String nickname,
            String profileImageUrl,
            String commentText,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.commentText = commentText;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getNickname() { return nickname; }
    public String getProfileImageUrl() { return profileImageUrl; }  // getter 추가
    public String getCommentText() { return commentText; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
