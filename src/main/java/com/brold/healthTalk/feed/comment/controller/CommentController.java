package com.brold.healthTalk.feed.comment.controller;

import com.brold.healthTalk.feed.comment.dto.request.CommentRequest;
import com.brold.healthTalk.feed.comment.dto.response.CommentResponse;
import com.brold.healthTalk.feed.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feeds/{feedId}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 목록 조회
    @GetMapping
    public ResponseEntity<List<CommentResponse>> list(
            @PathVariable Long feedId
    ) {
        return ResponseEntity.ok(commentService.listComments(feedId));
    }

    // 댓글 작성
    @PostMapping
    public ResponseEntity<CommentResponse> create(
            @PathVariable Long feedId,
            @RequestBody CommentRequest req
    ) {
        return ResponseEntity.status(201)
                .body(commentService.addComment(feedId, req));
    }

    // 댓글 수정
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> update(
            @PathVariable Long feedId,
            @PathVariable Long commentId,
            @RequestBody CommentRequest req
    ) {
        return ResponseEntity.ok(
                commentService.updateComment(feedId, commentId, req));
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long feedId,
            @PathVariable Long commentId,
            @RequestBody CommentRequest req
    ) {
        commentService.deleteComment(feedId, commentId, req.getUserId());
        return ResponseEntity.noContent().build();
    }
}
