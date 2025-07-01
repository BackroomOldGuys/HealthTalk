package com.brold.healthTalk.chat.dto;

import com.brold.healthTalk.chat.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ChatMessageDto {
    private Long id;
    private Long roomId;
    private Long senderId;
    private Long receiverId;   // ★ 추가
    private String content;
    private LocalDateTime createdAt;

    /**
     * ChatMessage 엔티티를 DTO로 변환
     */
    public static ChatMessageDto fromEntity(ChatMessage msg) {
        return new ChatMessageDto(
                msg.getId(),
                msg.getRoom().getId(),
                msg.getSenderId(),
                msg.getReceiverId(),
                msg.getContent(),
                msg.getCreatedAt()
        );
    }
}