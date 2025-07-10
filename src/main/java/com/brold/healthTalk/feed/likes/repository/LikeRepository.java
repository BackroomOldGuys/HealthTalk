package com.brold.healthTalk.feed.likes.repository;

import com.brold.healthTalk.feed.likes.domain.Like;
import com.brold.healthTalk.feed.likes.domain.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
    boolean existsByUserIdAndFeedId(Long userId, Long feedId);
    void deleteByUserIdAndFeedId(Long userId, Long feedId);
    long countByFeedId(Long feedId);
    List<Like> findAllByFeedId(Long feedId);
}
