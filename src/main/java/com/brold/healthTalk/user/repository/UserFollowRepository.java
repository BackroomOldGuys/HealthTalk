package com.brold.healthTalk.user.repository;

import com.brold.healthTalk.user.entity.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {
}
