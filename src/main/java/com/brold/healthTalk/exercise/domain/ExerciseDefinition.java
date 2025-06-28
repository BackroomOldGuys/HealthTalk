package com.brold.healthTalk.exercise.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "exercise_definitions")
public class ExerciseDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "target_area", nullable = false, length = 100)
    private String targetArea;

    // 기본 생성자
    protected ExerciseDefinition() {}

    public ExerciseDefinition(String name, String targetArea) {
        this.name = name;
        this.targetArea = targetArea;
    }

    // getter / setter
    public Integer getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTargetArea() { return targetArea; }
    public void setTargetArea(String targetArea) { this.targetArea = targetArea; }
}