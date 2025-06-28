package com.brold.healthTalk.user.repository;


import com.brold.healthTalk.user.entity.WorkoutGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutGoalRepository extends JpaRepository<WorkoutGoal, Long> {
    Optional<WorkoutGoal> findByName(String name);
}
