package com.brold.healthTalk.feed.tag.repository;

import com.brold.healthTalk.feed.tag.domain.FeedTag;
import com.brold.healthTalk.feed.tag.domain.FeedTagId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedTagRepository extends JpaRepository<FeedTag, FeedTagId> {
    List<FeedTag> findAllByFeedId(Long feedId);
    boolean existsByFeedIdAndTagId(Long feedId, Integer tagId);
    void deleteByFeedIdAndTagId(Long feedId, Integer tagId);
    List<FeedTag> findAllByTagId(Integer tagId);
}
