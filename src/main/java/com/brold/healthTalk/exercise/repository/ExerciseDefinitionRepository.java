package com.brold.healthTalk.exercise.repository;

import com.brold.healthTalk.exercise.domain.ExerciseDefinition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseDefinitionRepository extends JpaRepository<ExerciseDefinition, Integer> {
    List<ExerciseDefinition> findByNameContaining(String query, Pageable pageable);
}