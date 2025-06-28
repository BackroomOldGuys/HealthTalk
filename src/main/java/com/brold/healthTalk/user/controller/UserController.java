package com.brold.healthTalk.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    // 사용자 찾기
    @GetMapping("/search")
    private String search(){
        return null;
    }

    // 비밀번호 찾기
    @GetMapping("/searchpassword")
    private String findpw(){
        return null;
    }

    // 비밀번호 변경
    @PatchMapping("/changepassword")
    private String changepw(){
        return null;
    }

}
