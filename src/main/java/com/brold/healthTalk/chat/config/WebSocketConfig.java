package com.brold.healthTalk.chat.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws/chat")
                .setAllowedOrigins("http://127.0.0.1:5500", "http://localhost:8080")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트가 구독할 때 prefix
        registry.enableSimpleBroker("/topic", "/user");
        // @MessageMapping 으로 들어오는 메시지 prefix
        registry.setApplicationDestinationPrefixes("/app");
        // convertToUserDest 에서 사용할 prefix
        registry.setUserDestinationPrefix("/user");
    }
}