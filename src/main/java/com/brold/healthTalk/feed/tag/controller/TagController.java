package com.brold.healthTalk.feed.tag.controller;

import com.brold.healthTalk.feed.content.dto.response.FeedResponse;
import com.brold.healthTalk.feed.tag.dto.request.TagRequest;
import com.brold.healthTalk.feed.tag.dto.response.TagResponse;
import com.brold.healthTalk.feed.tag.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // 전체 태그 정의 조회
    @GetMapping("/tags")
    public ResponseEntity<List<TagResponse>> listAll() {
        return ResponseEntity.ok(tagService.listAllTags());
    }

    // 피드별 태그 목록 조회
    @GetMapping("/feeds/{feedId}/tags")
    public ResponseEntity<List<TagResponse>> listByFeed(@PathVariable Long feedId) {
        return ResponseEntity.ok(tagService.listTagsByFeed(feedId));
    }

    // 태그별 피드 조회
    @GetMapping("/tags/{tagId}/feeds")
    public ResponseEntity<List<FeedResponse>> listFeedsByTag(
            @PathVariable Integer tagId
    ) {
        List<FeedResponse> feeds = tagService.listFeedsByTag(tagId);
        return ResponseEntity.ok(feeds);
    }

    // 피드에 태그 추가
    @PostMapping("/feeds/{feedId}/tags")
    public ResponseEntity<Void> add(
            @PathVariable Long feedId,
            @RequestBody TagRequest req
    ) {
        tagService.addTagToFeed(feedId, req);
        return ResponseEntity.status(201).build();
    }

    // 피드에서 태그 삭제
    @DeleteMapping("/feeds/{feedId}/tags/{tagId}")
    public ResponseEntity<Void> remove(
            @PathVariable Long feedId,
            @PathVariable Integer tagId
    ) {
        tagService.removeTagFromFeed(feedId, tagId);
        return ResponseEntity.noContent().build();
    }
}
