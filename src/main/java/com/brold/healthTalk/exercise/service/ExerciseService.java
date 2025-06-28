package com.brold.healthTalk.exercise.service;

import com.brold.healthTalk.exercise.dto.request.ExerciseRequest;
import com.brold.healthTalk.exercise.dto.response.ExerciseResponse;

import java.util.List;

public interface ExerciseService {
    List<ExerciseResponse> searchExercises(String query);
    Integer createExercise(ExerciseRequest req);
    void updateExercise(Integer id, ExerciseRequest req);
    void deleteExercise(Integer id);
}