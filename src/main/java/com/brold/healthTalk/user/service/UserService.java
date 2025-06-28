package com.brold.healthTalk.user.service;


import com.brold.healthTalk.user.dto.response.ProfileInfoDto;
import com.brold.healthTalk.user.entity.User;
import com.brold.healthTalk.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User searchuser(String name){
        User user = userRepository.findByNickname(name).orElseThrow(() -> new IllegalArgumentException("해당 이메일 없다"));

        ProfileInfoDto profileInfoDto = new ProfileInfoDto();

        return user;
    }
}
