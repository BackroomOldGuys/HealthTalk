package com.brold.healthTalk.feed.comment.service;

import com.brold.healthTalk.feed.comment.domain.Comment;
import com.brold.healthTalk.feed.comment.repository.CommentRepository;
import com.brold.healthTalk.feed.content.repository.FeedRepository;
import com.brold.healthTalk.user.common.repository.UserRepository;
import com.brold.healthTalk.feed.comment.dto.response.CommentResponse;
import com.brold.healthTalk.feed.comment.dto.request.CommentRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepo;
    private final FeedRepository feedRepo;
    private final UserRepository userRepo;

    public CommentService(CommentRepository commentRepo,
                          FeedRepository feedRepo,
                          UserRepository userRepo) {
        this.commentRepo = commentRepo;
        this.feedRepo = feedRepo;
        this.userRepo = userRepo;
    }

    // 리스트 조회
    @Transactional(readOnly = true)
    public List<CommentResponse> listComments(Long feedId) {
        if (!feedRepo.existsById(feedId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feed not found");
        }
        return commentRepo.findAllByFeedIdOrderByCreatedAtAsc(feedId).stream()
                .map(c -> new CommentResponse(
                        c.getId(),
                        c.getUser().getId(),
                        c.getUser().getNickname(),
                        c.getUser().getProfileImageUrl(),   // 프로필 URL 포함
                        c.getCommentText(),
                        c.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    // 댓글 작성 반환 DTO 생성 시에도 profileImageUrl 포함
    @Transactional
    public CommentResponse addComment(Long feedId, CommentRequest req) {
        var feed = feedRepo.findById(feedId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feed not found"));
        var user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Comment comment = new Comment(feed, user, req.getCommentText());
        commentRepo.save(comment);

        return new CommentResponse(
                comment.getId(),
                user.getId(),
                user.getNickname(),
                user.getProfileImageUrl(),   // 프로필 URL 포함
                comment.getCommentText(),
                comment.getCreatedAt()
        );
    }

    @Transactional
    public CommentResponse updateComment(Long feedId, Long commentId, CommentRequest req) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Comment not found"));

        if (!comment.getFeed().getId().equals(feedId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Feed ID mismatch");
        }
        if (!comment.getUser().getId().equals(req.getUserId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "권한이 없습니다");
        }

        comment.setCommentText(req.getCommentText());

        return new CommentResponse(
                comment.getId(),
                comment.getUser().getId(),
                comment.getUser().getNickname(),
                comment.getUser().getProfileImageUrl(), // ← 추가
                comment.getCommentText(),
                comment.getCreatedAt()
        );
    }

    @Transactional
    public void deleteComment(Long feedId, Long commentId, Long userId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
        if (!comment.getFeed().getId().equals(feedId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Feed ID mismatch");
        }
        if (!comment.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다");
        }
        commentRepo.delete(comment);
    }
}
