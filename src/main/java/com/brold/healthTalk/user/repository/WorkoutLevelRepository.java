package com.brold.healthTalk.user.repository;


import com.brold.healthTalk.user.entity.WorkoutLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutLevelRepository extends JpaRepository<WorkoutLevel, Long> {
    Optional<WorkoutLevel> findByName(String name);
}
