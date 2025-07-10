# HealthApp Chat Module

## 1. 프로젝트 개요

* HealthApp 백엔드에 채팅 기능(클럽 채팅, 1:1 채팅)을 추가하는 모듈
* **Spring Boot** + **WebSocket**(STOMP) + **JPA**(MySQL) 기반
* 현재 JWT 인증 미적용 상태로, REST/WS 엔드포인트는 모두 인증 없이 열려 있음

## 2. 데이터베이스 구조

```sql
-- 채팅방 정보
CREATE TABLE chat_rooms (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  room_type VARCHAR(20) NOT NULL,      -- "CLUB" or "PRIVATE"
  club_id BIGINT NULL,                 -- clubs.id 참조 (클럽 채팅)
  conversation_id VARCHAR(100) UNIQUE  -- "userA_userB" (1:1 채팅)
);

-- 채팅방 참가자
CREATE TABLE chat_participants (
  room_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  PRIMARY KEY (room_id, user_id)
);

-- 메시지 로그
CREATE TABLE chat_messages (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  room_id BIGINT NOT NULL,
  sender_id BIGINT NULL,
  content   TEXT NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

* 외래키(FK) 제약조건 및 `ON DELETE / UPDATE` 옵션은 실제 DDL 참고
* **conversation\_id**는 클라이언트·서버 모두에서 `min(userA,userB) + "_" + max(...)` 형식으로 정규화

## 3. 패키지 구조

```
src/main/java/com/brold/healthTalk/chat/
├── config/        WebSocketConfig (STOMP, CORS)
├── controller/    ChatController (REST API: 과거 이력 조회)
├── dto/           ChatMessageDto (요청·응답 DTO)
├── domain/        ChatRoom, ChatParticipant, ChatMessage (JPA Entity)
├── repository/    ChatRoomRepository, ChatMessageRepository, …
├── service/       ChatService (로직: 방 생성·조회·메시지 발송·히스토리)
└── ws/            ClubChatHandler, PrivateChatHandler (@MessageMapping)
```

## 4. 주요 기능

### 4.1 클럽 채팅

* **방 구분**: `room_type = "CLUB"`, `club_id` 설정
* **전송**: 클라이언트 → `/app/chat/club/{clubId}/send`
* **구독**: 클라이언트 ← `/topic/club/{clubId}/messages`
* **흐름**:

    1. `ChatService.sendClubMessage`에서 DB 저장
    2. `template.convertAndSend("/topic/club/"+clubId+"/messages", msg)`

### 4.2 1:1 채팅

* **방 구분**: `room_type = "PRIVATE"`, `conversation_id = "userA_userB"` (정규화)
* **전송**: 클라이언트 → `/app/chat/private/send` (payload에 `senderId, receiverId, content`)
* **구독**: 클라이언트 ← `/topic/private/{conversationId}/msgs`
* **정규화**: `buildConversationId(a,b)`로 항상 `min_max` 형태 생성 → 중복 방 생성 방지
* **흐름**:

    1. `ChatService.sendPrivateMessage`에서 방 생성·DB 저장
    2. `template.convertAndSend("/topic/private/"+convId+"/msgs", msg)`

## 5. WebSocket 흐름

1. 클라이언트가 SockJS+STOMP로 `http://…/ws/chat` 에 CONNECT
2. `@MessageMapping("/chat/...")` 핸들러로 메시지 전달
3. `ChatService`가 메시지 저장
4. `SimpMessagingTemplate`이 `/topic/...`으로 발행
5. 구독 중인 클라이언트가 실시간 수신

> **현재 상태:** JWT 인증 미적용 → 모든 WS/REST 엔드포인트 `permitAll()` 상태

## 6. REST API (과거 이력 조회)

* **1:1 히스토리**

  ```
  GET /api/chats/private/{conversationId}/messages
    ?limit={n}&offset={m}
  → Page<ChatMessageDto>
  ```
* **클럽 히스토리**

  ```
  GET /api/chats/club/{clubId}/messages
    ?limit={n}&offset={m}
  → Page<ChatMessageDto>
  ```

**ChatMessageDto** 예시

```java
@Data @NoArgsConstructor @AllArgsConstructor
public class ChatMessageDto {
  private Long id;
  private Long roomId;
  private Long senderId;
  private String content;
  private LocalDateTime createdAt;

  public static ChatMessageDto fromEntity(ChatMessage e) { … }
}
```

## 7. 사용 방법

1. **환경 설정** (`application.yml`)

   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/healthtalk?serverTimezone=Asia/Seoul&useSSL=false
       username: YOUR_USER
       password: YOUR_PASS
       driver-class-name: com.mysql.cj.jdbc.Driver
     jpa:
       hibernate:
         ddl-auto: update
       show-sql: true
   ```
2. **서버 실행**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
3. **테스트**

    * **HTML+JS 클라이언트** 로 실시간 전송·구독
    * **Swagger UI** (`/swagger-ui/index.html`) 에서 REST 히스토리 조회

## 8. 기억할 포인트

* **conversationId 정규화**: `min(userA,userB) + "_" + max(...)`
* **room\_type** 으로 클럽 vs 1:1 분기
* 현재는 **JWT 인증 미적용** (추후 HandshakeInterceptor + Principal 설정 필요)
* **Foreign Key**: `sender_id` → users, `club_id` → clubs, `ON DELETE SET NULL`
* STOMP 설정:
  ```java
  registry.addEndpoint("/ws/chat")
          .setAllowedOrigins("…")
          .withSockJS();
  config.enableSimpleBroker("/topic", "/user");
  config.setApplicationDestinationPrefixes("/app");
  ```
