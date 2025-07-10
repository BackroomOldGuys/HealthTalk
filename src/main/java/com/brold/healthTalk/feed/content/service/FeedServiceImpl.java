package com.brold.healthTalk.feed.content.service;

import com.brold.healthTalk.feed.content.domain.Feed;
import com.brold.healthTalk.feed.content.domain.FeedExercise;
import com.brold.healthTalk.feed.content.dto.request.CreateFeedRequest;
import com.brold.healthTalk.feed.content.dto.request.UpdateFeedRequest;
import com.brold.healthTalk.feed.content.dto.response.FeedResponse;
import com.brold.healthTalk.feed.content.repository.FeedRepository;
import com.brold.healthTalk.feed.tag.domain.FeedTag;
import com.brold.healthTalk.feed.tag.domain.FeedTagId;
import com.brold.healthTalk.feed.tag.domain.Tag;
import com.brold.healthTalk.feed.tag.dto.response.TagResponse;
import com.brold.healthTalk.feed.tag.repository.FeedTagRepository;
import com.brold.healthTalk.feed.tag.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FeedServiceImpl implements FeedService {

    private final FeedRepository    feedRepo;
    private final FeedTagRepository feedTagRepo;
    private final TagRepository     tagRepo;

    @Autowired
    public FeedServiceImpl(FeedRepository feedRepo,
                           FeedTagRepository feedTagRepo,
                           TagRepository tagRepo) {
        this.feedRepo    = feedRepo;
        this.feedTagRepo = feedTagRepo;
        this.tagRepo     = tagRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedResponse> getAllFeeds(Pageable pageable) {
        return feedRepo.findAllByOrderByCreatedAtDesc(pageable)
                .map(feed -> {
                    // 조회된 각 feed에 달린 태그 목록을 TagResponse로 변환
                    List<TagResponse> tagDtos = feedTagRepo.findAllByFeedId(feed.getId()).stream()
                            .map(ft -> {
                                var tag = tagRepo.findById(ft.getTagId())
                                        .orElseThrow(() -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "Tag not found: " + ft.getTagId()));
                                return new TagResponse(tag.getId(), tag.getName());
                            })
                            .collect(Collectors.toList());
                    return FeedResponse.fromEntity(feed, tagDtos);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public FeedResponse getFeed(Long feedId) {
        Feed feed = feedRepo.findById(feedId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Feed not found: " + feedId));

        List<TagResponse> tagDtos = feedTagRepo.findAllByFeedId(feedId).stream()
                .map(ft -> {
                    var tag = tagRepo.findById(ft.getTagId())
                            .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.NOT_FOUND, "Tag not found: " + ft.getTagId()));
                    return new TagResponse(tag.getId(), tag.getName());
                })
                .collect(Collectors.toList());

        return FeedResponse.fromEntity(feed, tagDtos);
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

        // 3) 태그 매핑 설정
        if (req.getTags() != null) {
            req.getTags().forEach(tagId -> {
                Tag tag = tagRepo.findById(tagId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Tag not found: " + tagId));

                // FeedTag(feed, tag) 생성자 사용
                feedTagRepo.save(new FeedTag(feed, tag));
            });
        }

        // 4) 저장 (cascade 로 exercises도 함께 저장)
        return feedRepo.save(feed).getId();
    }

    @Override
    public FeedResponse updateFeed(Long feedId, UpdateFeedRequest req, Long currentUserId) {
        Feed feed = feedRepo.findById(feedId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Feed not found: " + feedId));
        if (!feed.getUserId().equals(currentUserId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "권한이 없습니다.");
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

        // 3) 태그 매핑 전체 교체
        // 기존 매핑 삭제
        feedTagRepo.findAllByFeedId(feedId).forEach(ft ->
                feedTagRepo.deleteByFeedIdAndTagId(feedId, ft.getTagId())
        );
        // 새 태그 매핑 추가
        if (req.getTags() != null) {
            req.getTags().forEach(tagId -> {
                Tag tag = tagRepo.findById(tagId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Tag not found: " + tagId));
                feedTagRepo.save(new FeedTag(feed, tag));
            });
        }

        // 4) FeedResponse 생성 (태그 포함)
        List<TagResponse> tagDtos = req.getTags() == null
                ? List.of()
                : req.getTags().stream()
                .map(id -> {
                    var tag = tagRepo.findById(id).get();
                    return new TagResponse(tag.getId(), tag.getName());
                })
                .collect(Collectors.toList());

        return FeedResponse.fromEntity(feed, tagDtos);
    }

    @Override
    public void deleteFeed(Long feedId, Long currentUserId) {
        Feed feed = feedRepo.findById(feedId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Feed not found: " + feedId));
        if (!feed.getUserId().equals(currentUserId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "권한이 없습니다.");
        }
        // 태그 매핑도 함께 삭제
        feedTagRepo.findAllByFeedId(feedId).forEach(ft ->
                feedTagRepo.deleteByFeedIdAndTagId(feedId, ft.getTagId())
        );
        feedRepo.delete(feed);
    }
}
