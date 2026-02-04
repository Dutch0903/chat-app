package com.chat_app.presentation.controller;

import com.chat_app.infrastructure.security.UserDetailsImpl;
import com.chat_app.presentation.request.CreateMessageRequest;
import com.chat_app.presentation.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatMessageController {

    @MessageMapping("/chat/{chatId}/send")
    @SendTo("/topic/chat/{chatId}")
    public MessageResponse sendMessage(
            @DestinationVariable UUID chatId,
            @Payload CreateMessageRequest message) {
        log.info("Message received for chat: {}, content: {}", chatId.toString(), message.getContent());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new MessageResponse(
                userDetails.getId().value(),
                message.getContent(),
                Instant.now()
        );
    }
}
