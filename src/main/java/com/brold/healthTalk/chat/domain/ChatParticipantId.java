package com.brold.healthTalk.chat.domain;

import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ChatParticipantId implements Serializable {
    private Long roomId;
    private Long userId;
}
