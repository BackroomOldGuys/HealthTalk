package com.brold.healthTalk.user.common.domain;

import com.brold.healthTalk.user.auth.domain.RefreshToken;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_discomforts", // 중간(조인) 테이블 이름
            joinColumns = @JoinColumn(name = "user_id"), // User 엔티티를 참조하는 외래 키
            inverseJoinColumns = @JoinColumn(name = "discomfort_id") // 반대쪽 엔티티(BodyDiscomfort)를 참조하는 외래 키
    )
    private Set<BodyDiscomfort> discomforts = new HashSet<>();

    @Formula("(SELECT COUNT(*) FROM user_follows uf WHERE uf.following_id = id)")
    private int followerCount;

    @Formula("(SELECT COUNT(*) FROM user_follows uf WHERE uf.follower_id = id)")
    private int followingCount;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefreshToken refreshToken;

    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
