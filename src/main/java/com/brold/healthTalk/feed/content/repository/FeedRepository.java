package com.brold.healthTalk.feed.content.repository;

import com.brold.healthTalk.feed.content.domain.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    Page<Feed> findAllByOrderByCreatedAtDesc(Pageable pageable);
}