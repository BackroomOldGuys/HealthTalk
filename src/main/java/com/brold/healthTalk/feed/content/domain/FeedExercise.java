package com.brold.healthTalk.feed.content.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "feed_exercises")
public class FeedExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Feed ↔ FeedExercise : 1:N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    // 운동 사전 참조
    @Column(name = "exercise_definition_id", nullable = false)
    private Integer exerciseDefinitionId;

    @Column(nullable = false)
    private Integer sets;

    @Column(nullable = false)
    private Integer reps;

    @Column(name = "weight_kg")
    private Double weightKg;

    @Column(name = "duration_min")
    private Integer durationMin;

    protected FeedExercise() {}

    public FeedExercise(Feed feed,
                        Integer exerciseDefinitionId,
                        Integer sets,
                        Integer reps,
                        Double weightKg,
                        Integer durationMin) {
        this.feed = feed;
        this.exerciseDefinitionId = exerciseDefinitionId;
        this.sets = sets;
        this.reps = reps;
        this.weightKg = weightKg;
        this.durationMin = durationMin;
    }

    // getters / setters ...
}
