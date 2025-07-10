package com.brold.healthTalk.chat.ws;

import com.brold.healthTalk.chat.dto.ChatMessageDto;
import com.brold.healthTalk.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ClubChatHandler {
    private final ChatService chatService;

    @MessageMapping("/chat/club/{clubId}/send")
    public void clubSend(
            @DestinationVariable Long clubId,
            @Payload ChatMessageDto dto
    ) {
        // Principal 제거, DTO 에 담긴 senderId 사용
        chatService.sendClubMessage(
                clubId,
                dto.getSenderId(),
                dto.getContent()
        );
    }
}