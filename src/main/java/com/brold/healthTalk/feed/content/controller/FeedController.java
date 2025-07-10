package com.brold.healthTalk.feed.content.controller;

import com.brold.healthTalk.feed.content.dto.request.CreateFeedRequest;
import com.brold.healthTalk.feed.content.dto.request.UpdateFeedRequest;
import com.brold.healthTalk.feed.content.dto.response.FeedResponse;
import com.brold.healthTalk.feed.content.service.FeedService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/feeds")
public class FeedController {

    private final FeedService service;

    @Autowired
    public FeedController(FeedService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<FeedResponse>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<FeedResponse> result = service.getAllFeeds(PageRequest.of(page, size));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedResponse> detail(@PathVariable Long id) {
        return ResponseEntity.ok(service.getFeed(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody CreateFeedRequest req
    ) {
        Long id = service.createFeed(req, 1L);
        URI location = URI.create("/api/feeds/" + id);
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FeedResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateFeedRequest req
    ) {
        FeedResponse updated = service.updateFeed(id, req, 1L);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteFeed(id, 1L);
        return ResponseEntity.noContent().build();
    }
}