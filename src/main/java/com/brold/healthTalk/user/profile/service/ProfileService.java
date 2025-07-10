package com.brold.healthTalk.user.profile.service;


import com.brold.healthTalk.user.common.domain.BodyDiscomfort;
import com.brold.healthTalk.user.common.domain.User;
import com.brold.healthTalk.user.profile.dto.request.ProfileUpdateDto;
import com.brold.healthTalk.user.profile.dto.response.ProfileInfoDto;
import com.brold.healthTalk.user.profile.repository.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserProfileRepository userProfileRepository;

    @Transactional
    public void profileupdate(ProfileUpdateDto profileUpdateDto){

    }

    // 내 프로필 조회
    // @Transactional(readOnly = true)를 붙이면 성능에 이점이 있습니다.
    // 특히 지연 로딩을 포함하는 경우 세션을 유지시켜주므로 필요합니다.
    @Transactional(readOnly = true)
    public ProfileInfoDto checkMyProfile(Long userId) {
        // 1. Repository를 통해 ID로 유저를 조회합니다.
        // findById는 Optional<User>를 반환하므로, 없을 경우 예외를 던집니다.
        User user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 유저를 찾을 수 없습니다: " + userId));

        // 2. 조회된 User 엔티티를 ProfileInfoDto로 변환합니다.
        return convertToProfileInfoDto(user);
    }

    // User 엔티티를 ProfileInfoDto로 변환하는 private 메소드
    private ProfileInfoDto convertToProfileInfoDto(User user) {
        // 불편 부위를 Set<BodyDiscomfort>에서 String으로 변환 (예: "무릎, 허리")
        String painArea = user.getDiscomforts().stream()
                .map(BodyDiscomfort::getName)
                .collect(Collectors.joining(", "));

        return ProfileInfoDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .gender(user.getGender().name())
                .age(user.getAge())
                .height(user.getHeightCm())
                .weight(user.getWeightKg())
                // workoutLevel이 null일 수 있으므로 null 체크
                .level(user.getWorkoutLevel() != null ? user.getWorkoutLevel().getName() : "미설정")
                .purpose(user.getWorkoutGoal() != null ? user.getWorkoutGoal().getName() : "미설정")
                .painArea(painArea.isEmpty() ? "없음" : painArea)
                .profileImageUrl(user.getProfileImageUrl())
                .followerCount(user.getFollowerCount()) // @Formula 필드는 바로 접근 가능
                .followingCount(user.getFollowingCount()) // @Formula 필드는 바로 접근 가능
                .build();
    }
}
