package com.brold.healthTalk.chat.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@IdClass(ChatParticipantId.class)
@Table(name = "chat_participants")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ChatParticipant {
    @Id
    private Long roomId;

    @Id
    private Long userId;
}