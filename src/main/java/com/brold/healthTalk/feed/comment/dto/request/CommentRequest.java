package com.brold.healthTalk.feed.comment.dto.request;

public class CommentRequest {
    private Long userId;
    private String commentText;

    public CommentRequest() {}

    public CommentRequest(Long userId, String commentText) {
        this.userId = userId;
        this.commentText = commentText;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCommentText() {
        return commentText;
    }
}
