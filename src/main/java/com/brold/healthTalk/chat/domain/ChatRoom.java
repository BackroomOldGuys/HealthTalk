package com.brold.healthTalk.chat.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_rooms")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;           // "CLUB" or "PRIVATE"

    @Column
    private Long clubId;               // 클럽 채팅일 때

    @Column(unique = true)
    private String conversationId;     // 1:1 채팅일 때 ("userA_userB")

    public enum RoomType {
        CLUB, PRIVATE
    }
}

