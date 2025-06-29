package com.brold.healthTalk.feed.content.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateFeedRequest {

    @NotBlank @Size(max = 200)
    private String content;

    @Valid
    private List<ExerciseRecord> exercises;

    private List<Integer> tags;

    //const
    public CreateFeedRequest() {}

    public CreateFeedRequest(String content,
                             List<ExerciseRecord> exercises,
                             List<Integer> tags) {
        this.content   = content;
        this.exercises = exercises;
        this.tags      = tags;
    }

    // inner DTO
    @Data
    public static class ExerciseRecord {
        @NotNull
        private Integer exerciseDefinitionId;
        @NotNull private Integer sets;
        @NotNull private Integer reps;
        private Double weightKg;
        private Integer durationMin;
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}