package com.brold.healthTalk.feed.tag.service;

import com.brold.healthTalk.feed.content.domain.Feed;
import com.brold.healthTalk.feed.content.dto.response.FeedResponse;
import com.brold.healthTalk.feed.tag.domain.Tag;
import com.brold.healthTalk.feed.tag.domain.FeedTag;
import com.brold.healthTalk.feed.tag.domain.FeedTagId;
import com.brold.healthTalk.feed.tag.dto.request.TagRequest;
import com.brold.healthTalk.feed.tag.dto.response.TagResponse;
import com.brold.healthTalk.feed.tag.repository.TagRepository;
import com.brold.healthTalk.feed.tag.repository.FeedTagRepository;
import com.brold.healthTalk.feed.content.repository.FeedRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository     tagRepo;
    private final FeedRepository    feedRepo;
    private final FeedTagRepository feedTagRepo;

    public TagService(TagRepository tagRepo,
                      FeedRepository feedRepo,
                      FeedTagRepository feedTagRepo) {
        this.tagRepo     = tagRepo;
        this.feedRepo    = feedRepo;
        this.feedTagRepo = feedTagRepo;
    }

    @Transactional(readOnly = true)
    public List<TagResponse> listAllTags() {
        return tagRepo.findAll().stream()
                .map(t -> new TagResponse(t.getId(), t.getName()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TagResponse> listTagsByFeed(Long feedId) {
        if (!feedRepo.existsById(feedId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feed not found");
        }
        return feedTagRepo.findAllByFeedId(feedId).stream()
                .map(ft -> new TagResponse(ft.getTag().getId(), ft.getTag().getName()))
                .collect(Collectors.toList());
    }

    //특정 태그가 달린 피드 목록 반환
    @Transactional(readOnly = true)
    public List<FeedResponse> listFeedsByTag(Integer tagId) {
        // 1) 태그 존재 여부 검사
        tagRepo.findById(tagId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Tag not found"));

        // 2) 해당 태그가 매핑된 feedId 목록 조회
        List<Long> feedIds = feedTagRepo.findAllByTagId(tagId).stream()
                .map(FeedTag::getFeedId)
                .distinct()
                .collect(Collectors.toList());

        // 3) 각 피드마다 태그 목록을 DTO로 조회
        List<FeedResponse> result = feedRepo.findAllById(feedIds).stream()
                .map(feed -> {
                    // 태그 리스트 구하기
                    List<TagResponse> tagDtos = feedTagRepo.findAllByFeedId(feed.getId()).stream()
                            .map(ft -> new TagResponse(ft.getTag().getId(), ft.getTag().getName()))
                            .collect(Collectors.toList());

                    // FeedResponse 생성 (exercises 포함)
                    return FeedResponse.fromEntity(feed, tagDtos);
                })
                .collect(Collectors.toList());

        return result;
    }

    @Transactional
    public void addTagToFeed(Long feedId, TagRequest req) {
        Integer tagId = req.getTagId();
        Feed feed = feedRepo.findById(feedId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Feed not found"));
        Tag tag = tagRepo.findById(tagId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Tag not found"));

        if (feedTagRepo.existsByFeedIdAndTagId(feedId, tagId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Tag already added");
        }

        // FeedTag(feed, tag) 생성자 호출
        feedTagRepo.save(new FeedTag(feed, tag));
    }


    @Transactional
    public void removeTagFromFeed(Long feedId, Integer tagId) {
        FeedTagId id = new FeedTagId(feedId, tagId);
        if (!feedTagRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag mapping not found");
        }
        feedTagRepo.deleteByFeedIdAndTagId(feedId, tagId);
    }
}
