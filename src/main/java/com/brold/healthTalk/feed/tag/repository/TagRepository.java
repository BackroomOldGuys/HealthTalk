package com.brold.healthTalk.feed.tag.repository;

import com.brold.healthTalk.feed.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    // 추가 커스텀 메서드 필요 시 여기에 선언
}
