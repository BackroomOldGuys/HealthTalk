package com.brold.healthTalk.chat.repository;


import com.brold.healthTalk.chat.domain.ChatParticipant;
import com.brold.healthTalk.chat.domain.ChatParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatParticipantRepository
        extends JpaRepository<ChatParticipant, ChatParticipantId> { }
