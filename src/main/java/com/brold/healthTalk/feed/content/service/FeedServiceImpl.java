package com.brold.healthTalk.feed.content.service;

import com.brold.healthTalk.feed.content.domain.Feed;
import com.brold.healthTalk.feed.content.domain.FeedExercise;
import com.brold.healthTalk.feed.content.dto.request.CreateFeedRequest;
import com.brold.healthTalk.feed.content.dto.request.UpdateFeedRequest;
import com.brold.healthTalk.feed.content.dto.response.FeedResponse;
import com.brold.healthTalk.feed.content.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FeedServiceImpl implements FeedService {

    private final FeedRepository repo;

    @Autowired
    public FeedServiceImpl(FeedRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedResponse> getAllFeeds(Pageable pageable) {
        return repo.findAllByOrderByCreatedAtDesc(pageable)
                .map(FeedResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public FeedResponse getFeed(Long feedId) {
        Feed feed = repo.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("Feed not found: " + feedId));
        return FeedResponse.fromEntity(feed);
    }

    @Override
    public Long createFeed(CreateFeedRequest req, Long currentUserId) {
        // 1) Feed 엔티티 생성
        Feed feed = new Feed(currentUserId, req.getContent(), null);

        // 2) ExerciseRecord → FeedExercise 변환 후 연관관계 설정
        req.getExercises().forEach(er -> {
            FeedExercise fe = new FeedExercise(
                    feed,
                    er.getExerciseDefinitionId(),
                    er.getSets(),
                    er.getReps(),
                    er.getWeightKg(),
                    er.getDurationMin()
            );
            feed.addExercise(fe);
        });

        // 3) 저장 (cascade 로 feed_exercises도 함께 저장)
        return repo.save(feed).getId();
    }

    @Override
    public FeedResponse updateFeed(Long feedId, UpdateFeedRequest req, Long currentUserId) {
        Feed feed = repo.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("Feed not found: " + feedId));
        if (!feed.getUserId().equals(currentUserId)) {
            throw new SecurityException("권한이 없습니다.");
        }
        // 1) 본문 내용 업데이트
        feed.setContent(req.getContent());

        // 2) 운동 기록 전체 교체
        feed.clearExercises();  // orphanRemoval로 DB에서도 삭제
        req.getExercises().forEach(er -> {
            FeedExercise fe = new FeedExercise(
                    feed,
                    er.getExerciseDefinitionId(),
                    er.getSets(),
                    er.getReps(),
                    er.getWeightKg(),
                    er.getDurationMin()
            );
            feed.addExercise(fe);
        });

        // 3) 저장은 트랜잭션 커밋 시 자동 실행 (dirty checking)
        return FeedResponse.fromEntity(feed);
    }

    @Override
    public void deleteFeed(Long feedId, Long currentUserId) {
        Feed feed = repo.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("Feed not found: " + feedId));
        if (!feed.getUserId().equals(currentUserId)) {
            throw new SecurityException("권한이 없습니다.");
        }
        repo.delete(feed);
    }

}