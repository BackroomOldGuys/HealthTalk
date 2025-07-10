package com.brold.healthTalk.user.common.repository;


import com.brold.healthTalk.user.common.domain.WorkoutLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutLevelRepository extends JpaRepository<WorkoutLevel, Long> {
    Optional<WorkoutLevel> findByName(String name);
}
