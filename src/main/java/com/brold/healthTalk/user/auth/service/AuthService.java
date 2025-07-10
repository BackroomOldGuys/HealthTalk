package com.brold.healthTalk.user.auth.service;


import com.brold.healthTalk.user.auth.dto.request.LoginDto;
import com.brold.healthTalk.user.auth.dto.request.SignUpDto;
import com.brold.healthTalk.user.common.domain.User;
import com.brold.healthTalk.user.common.domain.WorkoutGoal;
import com.brold.healthTalk.user.common.domain.WorkoutLevel;
import com.brold.healthTalk.user.auth.repository.AuthRepository;
import com.brold.healthTalk.user.common.repository.WorkoutGoalRepository;
import com.brold.healthTalk.user.common.repository.WorkoutLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final WorkoutGoalRepository workoutGoalRepository;
    private final WorkoutLevelRepository workoutLevelRepository;

    @Transactional
    public Long login(LoginDto loginDto){
        User user = authRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("해당 이메일 없다"));

        if(!user.getPassword().equals(loginDto.getPassword())){
            throw  new IllegalArgumentException("비번 일치 X");
        }

        return user.getId();

    }

    @Transactional
    public void signup(SignUpDto signUpDto){

        // WorkoutLevel, WorkoutGoal 엔티티 조회
        WorkoutLevel level = workoutLevelRepository.findByName(signUpDto.getLevel())
                .orElseThrow(() -> new IllegalArgumentException("운동 수준이 유효하지 않습니다."));

        WorkoutGoal goal = workoutGoalRepository.findByName(signUpDto.getPurpose())
                .orElseThrow(() -> new IllegalArgumentException("운동 목적이 유효하지 않습니다."));

        // Gender enum 파싱
        User.Gender gender = User.Gender.valueOf(signUpDto.getGender().toUpperCase());

        // User 엔티티 생성
        User user = User.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword()) // → 실제로는 암호화 필요!
                .nickname(signUpDto.getNickname())
                .gender(gender)
                .age(signUpDto.getAge())
                .heightCm((float) signUpDto.getHeight())
                .weightKg((float) signUpDto.getWeight())
                .profileImageUrl(signUpDto.getProfileImageUrl())
                .workoutLevel(level)
                .workoutGoal(goal)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        authRepository.save(user);

    }


}
