package com.brold.healthTalk.chat.repository;


import com.brold.healthTalk.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByConversationId(String conversationId);
}