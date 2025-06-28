package com.brold.healthTalk.user.repository;

import com.brold.healthTalk.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<User, Long> {
}
