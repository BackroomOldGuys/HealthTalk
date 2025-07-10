package com.brold.healthTalk.feed.comment.repository;

import com.brold.healthTalk.feed.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByFeedIdOrderByCreatedAtAsc(Long feedId);
}
