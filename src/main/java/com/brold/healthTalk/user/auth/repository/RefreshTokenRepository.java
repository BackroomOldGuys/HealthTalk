package com.brold.healthTalk.user.auth.repository;

import com.brold.healthTalk.user.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
