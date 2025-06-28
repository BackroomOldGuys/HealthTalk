package com.brold.healthTalk.user.service;


import com.brold.healthTalk.user.dto.request.ProfileUpdateDto;
import com.brold.healthTalk.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserProfileRepository userProfileRepository;

    @Transactional
    public void profileupdate(ProfileUpdateDto profileUpdateDto){

    }
}
