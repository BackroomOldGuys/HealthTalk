package com.brold.healthTalk.feed.likes.service;

import com.brold.healthTalk.feed.content.domain.Feed;
import com.brold.healthTalk.feed.likes.domain.Like;
import com.brold.healthTalk.feed.likes.repository.LikeRepository;
import com.brold.healthTalk.feed.content.repository.FeedRepository;
import com.brold.healthTalk.user.common.domain.User;
import com.brold.healthTalk.user.common.repository.UserRepository;
import com.brold.healthTalk.feed.likes.dto.response.UserResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {
    private final LikeRepository likeRepo;
    private final FeedRepository feedRepo;
    private final UserRepository userRepo;

    public LikeService(LikeRepository likeRepo,
                       FeedRepository feedRepo,
                       UserRepository userRepo) {
        this.likeRepo = likeRepo;
        this.feedRepo = feedRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public void addLike(Long feedId, Long userId) {
        if (likeRepo.existsByUserIdAndFeedId(userId, feedId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,   // 400 Bad Request
                    "이미 좋아요를 누르셨습니다."
            );
        }
        // 피드 존재 여부 검사 → 404로 변경
        Feed feed = feedRepo.findById(feedId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Feed not found"
                ));

        // 사용자 존재 여부 검사 → 404로 변경
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        // 저장
        likeRepo.save(new Like(userId, feedId));

    }

    @Transactional
    public void removeLike(Long feedId, Long userId) {
        if (!likeRepo.existsByUserIdAndFeedId(userId, feedId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "좋아요가 없습니다."
            );
        }
        likeRepo.deleteByUserIdAndFeedId(userId, feedId);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getLikers(Long feedId) {
        return likeRepo.findAllByFeedId(feedId).stream()
                .map(like -> new UserResponseDto(
                        like.getUser().getId(),
                        like.getUser().getNickname(),
                        like.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}
