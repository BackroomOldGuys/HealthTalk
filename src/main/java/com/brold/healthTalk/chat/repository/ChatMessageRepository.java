package com.brold.healthTalk.chat.repository;

import com.brold.healthTalk.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Page<ChatMessage> findByRoom_ConversationIdOrderByCreatedAtAsc(
            String conversationId, Pageable pageable);

    Page<ChatMessage> findByRoomIdOrderByCreatedAtAsc(Long roomId, Pageable pageable);
}