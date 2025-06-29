package com.brold.healthTalk.user.common.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "workout_goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
