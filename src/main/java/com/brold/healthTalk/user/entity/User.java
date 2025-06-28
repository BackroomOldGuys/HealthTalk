package com.brold.healthTalk.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer age;
    private Float heightCm;
    private Float weightKg;
    private String profileImageUrl;

    // 자기소개
    private String bio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_level_id")
    private WorkoutLevel workoutLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_goal_id")
    private WorkoutGoal workoutGoal;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefreshToken refreshToken;

    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
