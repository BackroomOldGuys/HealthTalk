package com.brold.healthTalk.user.profile.repository;

import com.brold.healthTalk.user.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<User, Long> {
}
