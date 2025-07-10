package com.brold.healthTalk.user.profile.controller;

import com.brold.healthTalk.user.common.domain.User;
import com.brold.healthTalk.user.profile.dto.response.ProfileInfoDto;
import com.brold.healthTalk.user.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // 내프로필 조회
    @GetMapping("/myprofile")
    public ResponseEntity<ProfileInfoDto> myprofile(@RequestParam("userId") Long userId) {
        // 서비스 호출 시 Long 타입의 ID를 전달
        ProfileInfoDto myProfile = profileService.checkMyProfile(userId);

        // 서비스로부터 받은 DTO를 HTTP 200 OK 상태와 함께 반환
        return ResponseEntity.ok(myProfile);
    }

    // 다른 유저 프로필 조회
    // pathvariable인 userid 기반으로 IDOR 공격을 막기 위해 추후 변경 필요
    @GetMapping("/{userId}/profile")
    public ResponseEntity<ProfileInfoDto> userprofile(@PathVariable("userId") Long userId) {
        ProfileInfoDto userProfile = profileService.checkMyProfile(userId);
        return ResponseEntity.ok(userProfile);
    }

    // 내 프로필 수정
    @PatchMapping("/myprofile")
    private String updatemyprofile(){
        return null;
    }

}
