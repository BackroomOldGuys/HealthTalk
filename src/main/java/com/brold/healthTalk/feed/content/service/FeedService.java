package com.brold.healthTalk.feed.content.service;

import com.brold.healthTalk.feed.content.dto.request.CreateFeedRequest;
import com.brold.healthTalk.feed.content.dto.request.UpdateFeedRequest;
import com.brold.healthTalk.feed.content.dto.response.FeedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedService {
    Page<FeedResponse> getAllFeeds(Pageable pageable);
    FeedResponse getFeed(Long feedId);
    Long createFeed(CreateFeedRequest req, Long currentUserId);
    FeedResponse updateFeed(Long feedId, UpdateFeedRequest req, Long currentUserId);
    void deleteFeed(Long feedId, Long currentUserId);
}