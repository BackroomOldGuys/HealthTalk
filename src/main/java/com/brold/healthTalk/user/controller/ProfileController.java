package com.brold.healthTalk.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class ProfileController {

    // 내프로필 조회
    @GetMapping("/myprofile")
    private String myprofile(){
        return null;
    }

    // 유저 프로필 조회
    @GetMapping("/{userId}/profile")
    private String userprofile(){
        return null;
    }

    // 내 프로필 수정
    @PatchMapping("/myprofile")
    private String updatemyprofile(){
        return null;
    }

}
