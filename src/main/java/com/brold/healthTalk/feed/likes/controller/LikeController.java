package com.brold.healthTalk.feed.likes.controller;

import com.brold.healthTalk.feed.likes.dto.request.LikeRequest;
import com.brold.healthTalk.feed.likes.dto.response.UserResponseDto;
import com.brold.healthTalk.feed.likes.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feeds/{feedId}/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    // 좋아요 누르기
    @PostMapping
    public ResponseEntity<Void> like(
            @PathVariable Long feedId,
            @RequestBody LikeRequest req
    ) {
        Long actorId = req.getUserId();
        likeService.addLike(feedId, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 좋아요 취소
    @DeleteMapping
    public ResponseEntity<Void> unlike(
            @PathVariable Long feedId,
            @RequestBody LikeRequest req
    ) {
        Long actorId = req.getUserId();
        likeService.removeLike(feedId, actorId);
        return ResponseEntity.noContent().build();
    }

    // 좋아요한 사람들 목록 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> listLikers(
            @PathVariable Long feedId
    ) {
        List<UserResponseDto> likers = likeService.getLikers(feedId);
        return ResponseEntity.ok(likers);
    }
}
