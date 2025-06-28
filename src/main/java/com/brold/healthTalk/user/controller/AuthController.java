package com.brold.healthTalk.user.controller;


import com.brold.healthTalk.user.dto.request.LoginDto;
import com.brold.healthTalk.user.dto.request.SignUpDto;
import com.brold.healthTalk.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    private ResponseEntity<String> signup(@RequestBody SignUpDto signUpDto){
        authService.signup(signUpDto);
        return ResponseEntity.ok("로그인 성공. 사용자 ID : " + signUpDto);
    }

    // 로그인
    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody LoginDto loginDto){

        Long userId = authService.login(loginDto);
        return ResponseEntity.ok("로그인 성공. 사용자 ID : " + userId);
    }

    // 로그아웃
    @PostMapping("/logout")
    private String logout(){
        return null;
    }

    // 토큰 재발급
    @PostMapping("/reissue")
    private String reissue(){
        return null;
    }
}
