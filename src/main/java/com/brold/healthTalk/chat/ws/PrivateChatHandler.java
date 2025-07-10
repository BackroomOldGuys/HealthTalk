package com.brold.healthTalk.chat.ws;

import com.brold.healthTalk.chat.dto.ChatMessageDto;
import com.brold.healthTalk.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PrivateChatHandler {
    private final ChatService chatService;

    /**
     * 1:1 채팅 메시지 수신
     * (conversationId는 서버에서 buildConversationId()로 일관 생성)
     */
    @MessageMapping("/chat/private/send")
    public void privateSend(@Payload ChatMessageDto dto) {
        chatService.sendPrivateMessage(
                dto.getSenderId(),
                dto.getReceiverId(),
                dto.getContent()
        );
    }
}
