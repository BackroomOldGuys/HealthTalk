package com.brold.healthTalk.chat.controller;

import com.brold.healthTalk.chat.dto.ChatMessageDto;
import com.brold.healthTalk.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {
    private final ChatService chatService;

    /**
     * 1:1 대화 이력 조회
     *
     * @param conversationId 대화방 식별자 (예: "userA_userB")
     * @param limit          한 번에 가져올 메시지 개수
     * @param offset         페이지의 시작 인덱스 (0부터)
     * @return 페이징된 ChatMessageDto 목록
     */
    @Operation(
            summary = "1:1 대화 이력 조회",
            description = "conversationId 에 해당하는 1:1 채팅 메시지를 limit, offset 기반으로 페이징하여 반환합니다."
    )
    @GetMapping("/private/{conversationId}/messages")
    public Page<ChatMessageDto> getHistory(
            @Parameter(description = "대화방 식별자, userA_userB 형식", required = true)
            @PathVariable String conversationId,

            @Parameter(description = "한 페이지에 가져올 메시지 수", example = "20", required = true)
            @RequestParam int limit,

            @Parameter(description = "조회 시작 인덱스 (0부터)", example = "0", required = true)
            @RequestParam int offset
    ) {
        Page<ChatMessageDto> page = chatService
                .getPrivateHistory(conversationId, limit, offset)
                .map(ChatMessageDto::fromEntity);
        return page;
    }

    /**
     * 클럽 채팅 이력 조회
     *
     * @param clubId  조회할 클럽의 ID
     * @param limit   한 번에 가져올 메시지 개수
     * @param offset  페이지의 시작 인덱스 (0부터)
     * @return 페이징된 ChatMessageDto 목록
     */
    @Operation(
            summary = "클럽 채팅 이력 조회",
            description = "clubId 에 해당하는 클럽 채팅 메시지를 limit, offset 기반으로 페이징하여 반환합니다."
    )
    @GetMapping("/club/{clubId}/messages")
    public Page<ChatMessageDto> getClubHistory(
            @Parameter(description = "클럽 ID", required = true)
            @PathVariable Long clubId,

            @Parameter(description = "한 페이지에 가져올 메시지 수", example = "20", required = true)
            @RequestParam int limit,

            @Parameter(description = "조회 시작 인덱스 (0부터)", example = "0", required = true)
            @RequestParam int offset
    ) {
        return chatService
                .getClubHistory(clubId, limit, offset)
                .map(ChatMessageDto::fromEntity);
    }

}
