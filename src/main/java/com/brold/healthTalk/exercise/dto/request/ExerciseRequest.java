package com.brold.healthTalk.exercise.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ExerciseRequest {

    @NotBlank @Size(max = 100)
    private String name;

    @NotBlank @Size(max = 100)
    private String targetArea;

    // getter / setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTargetArea() { return targetArea; }
    public void setTargetArea(String targetArea) { this.targetArea = targetArea; }
}
