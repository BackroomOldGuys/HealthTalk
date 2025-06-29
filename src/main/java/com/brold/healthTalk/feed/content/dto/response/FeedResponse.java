package com.brold.healthTalk.feed.content.dto.response;

import com.brold.healthTalk.feed.content.domain.Feed;
import com.brold.healthTalk.feed.content.domain.FeedExercise;
import com.brold.healthTalk.feed.tag.dto.response.TagResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedResponse {

    private Long id;
    private Long authorId;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 1) 운동 기록을 담을 리스트 필드 추가
    private List<ExerciseRecord> exercises;
    private List<TagResponse> tags;

    public FeedResponse(Long id,
                        Long authorId,
                        String content,
                        String imageUrl,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt,
                        List<ExerciseRecord> exercises) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.exercises = exercises;
    }

    public FeedResponse(Long id,
                        Long authorId,
                        String content,
                        String imageUrl,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt,
                        List<ExerciseRecord> exercises,
                        List<TagResponse> tags) {
        this.id        = id;
        this.authorId  = authorId;
        this.content   = content;
        this.imageUrl  = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.exercises = exercises;
        this.tags      = tags;
    }

    public static FeedResponse fromEntity(Feed f) {
        // 2) FeedExercise → ExerciseRecord 매핑
        List<ExerciseRecord> records = f.getExercises().stream()
                .map(FeedResponse::toRecord)
                .collect(Collectors.toList());

        return new FeedResponse(
                f.getId(),
                f.getUserId(),
                f.getContent(),
                f.getImageUrl(),
                f.getCreatedAt(),
                f.getUpdatedAt(),
                records
        );
    }

    public static FeedResponse fromEntity(Feed f, List<TagResponse> tags) {
        List<ExerciseRecord> records = f.getExercises().stream()
                .map(FeedResponse::toRecord)
                .collect(Collectors.toList());
        return new FeedResponse(
                f.getId(),
                f.getUserId(),
                f.getContent(),
                f.getImageUrl(),
                f.getCreatedAt(),
                f.getUpdatedAt(),
                records,
                tags
        );
    }

    private static ExerciseRecord toRecord(FeedExercise fe) {
        return new ExerciseRecord(
                fe.getExerciseDefinitionId(),
                fe.getSets(),
                fe.getReps(),
                fe.getWeightKg(),
                fe.getDurationMin()
        );
    }

    // getters...

    // 3) 내부 static DTO 클래스
    @Data
    public static class ExerciseRecord {
        private Integer exerciseDefinitionId;
        private Integer sets;
        private Integer reps;
        private Double weightKg;
        private Integer durationMin;

        public ExerciseRecord(Integer exerciseDefinitionId,
                              Integer sets,
                              Integer reps,
                              Double weightKg,
                              Integer durationMin) {
            this.exerciseDefinitionId = exerciseDefinitionId;
            this.sets = sets;
            this.reps = reps;
            this.weightKg = weightKg;
            this.durationMin = durationMin;
        }

        // getters...
    }
}
