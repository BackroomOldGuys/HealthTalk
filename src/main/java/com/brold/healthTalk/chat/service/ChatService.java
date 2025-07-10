package com.brold.healthTalk.chat.service;

import com.brold.healthTalk.chat.domain.ChatMessage;
import com.brold.healthTalk.chat.domain.ChatParticipant;
import com.brold.healthTalk.chat.domain.ChatRoom;
import com.brold.healthTalk.chat.repository.ChatParticipantRepository;
import com.brold.healthTalk.chat.repository.ChatMessageRepository;
import com.brold.healthTalk.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository roomRepo;
    private final ChatParticipantRepository participantRepo;
    private final ChatMessageRepository messageRepo;
    private final SimpMessagingTemplate template;

    /**
     * userA, userB 중 작은 ID가 앞에 오도록 정규화
     */
    private String buildConversationId(Long userA, Long userB) {
        return (userA < userB)
                ? userA + "_" + userB
                : userB + "_" + userA;
    }

    public ChatRoom getOrCreateClubRoom(Long clubId) {
        return roomRepo.findAll().stream()
                .filter(r -> "CLUB".equals(r.getRoomType()) && clubId.equals(r.getClubId()))
                .findFirst()
                .orElseGet(() -> {
                    ChatRoom r = new ChatRoom();
                    r.setRoomType(ChatRoom.RoomType.valueOf("CLUB"));
                    r.setClubId(clubId);
                    return roomRepo.save(r);
                });
    }

    /**
     * 두 사용자의 conversationId를 정규화해서 1:1 채팅방 생성/조회
     */
    public ChatRoom getOrCreatePrivateRoom(Long userA, Long userB) {
        String convId = buildConversationId(userA, userB);
        return roomRepo.findByConversationId(convId)
                .orElseGet(() -> {
                    ChatRoom r = new ChatRoom();
                    r.setRoomType(ChatRoom.RoomType.valueOf("PRIVATE"));
                    r.setConversationId(convId);
                    ChatRoom saved = roomRepo.save(r);
                    // 참가자 등록
                    participantRepo.save(new ChatParticipant(saved.getId(), userA));
                    participantRepo.save(new ChatParticipant(saved.getId(), userB));
                    return saved;
                });
    }

    /**
     * 클럽 채팅 전송
     */
    public void sendClubMessage(Long clubId, Long senderId, String content) {
        ChatRoom room = getOrCreateClubRoom(clubId);
        ChatMessage msg = new ChatMessage();
        msg.setRoom(room);
        msg.setSenderId(senderId);
        msg.setContent(content);
        messageRepo.save(msg);
        template.convertAndSend(
                "/topic/club/" + clubId + "/messages",
                msg
        );
    }

    /**
     * 1:1 채팅 전송 (정규화된 conversationId 사용)
     */
    public void sendPrivateMessage(Long senderId, Long receiverId, String content) {
        // key 생성 순서 보장
        ChatRoom room = getOrCreatePrivateRoom(senderId, receiverId);
        ChatMessage msg = new ChatMessage();
        msg.setRoom(room);
        msg.setSenderId(senderId);
        msg.setContent(content);
        messageRepo.save(msg);

        // topic 기반으로 발행
        String convId = buildConversationId(senderId, receiverId);
        template.convertAndSend(
                "/topic/private/" + convId + "/msgs",
                msg
        );
    }

    public Page<ChatMessage> getPrivateHistory(String conversationId, int limit, int offset) {
        Pageable page = PageRequest.of(offset/limit, limit);
        return messageRepo.findByRoom_ConversationIdOrderByCreatedAtAsc(conversationId, page);
    }

    public Page<ChatMessage> getClubHistory(Long clubId, int limit, int offset) {
        ChatRoom room = getOrCreateClubRoom(clubId);
        Pageable page = PageRequest.of(offset / limit, limit);
        return messageRepo.findByRoomIdOrderByCreatedAtAsc(room.getId(), page);
    }
}
