package com.brold.healthTalk.user.common.repository;


import com.brold.healthTalk.user.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
}
