package com.brold.healthTalk.user.follow.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class FollowController {

    // 팔로우
    @PostMapping("/{userId}/follow")
    private String follow(){
        return null;
    }

    // 언팔로우
    @DeleteMapping("/{userId}/follow")
    private String unfollow(){
        return null;
    }

    // 팔로잉 조회
    @GetMapping("/{userId}/followings")
    private String searchfollowing(){
        return null;
    }

    // 팔로워 조회
    @GetMapping("/{userId}/followers")
    private String searchfollowers(){
        return null;
    }
}
