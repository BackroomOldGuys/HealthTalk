package com.brold.healthTalk.user.follow.service;

import com.brold.healthTalk.user.follow.repository.UserFollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserFollowRepository userFollowRepository;


}
