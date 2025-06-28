package com.brold.healthTalk.exercise.dto.response;

import com.brold.healthTalk.exercise.domain.ExerciseDefinition;

public class ExerciseResponse {

    private Integer id;
    private String name;

    public ExerciseResponse(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ExerciseResponse fromEntity(ExerciseDefinition e) {
        return new ExerciseResponse(e.getId(), e.getName());
    }

    // getter
    public Integer getId() { return id; }
    public String getName() { return name; }
}
