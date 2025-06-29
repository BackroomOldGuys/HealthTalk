package com.brold.healthTalk.user.common.repository;


import com.brold.healthTalk.user.common.domain.WorkoutGoal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutGoalRepository extends JpaRepository<WorkoutGoal, Long> {
    Optional<WorkoutGoal> findByName(String name);
}
