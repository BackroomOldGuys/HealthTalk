package com.brold.healthTalk.user.follow.repository;

import com.brold.healthTalk.user.follow.domain.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {
}
