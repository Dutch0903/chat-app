package com.chat_app.controller;

import com.chat_app.entity.Message;
import com.chat_app.dto.ChatDetailsDto;
import com.chat_app.dto.ChatDto;
import com.chat_app.security.UserDetailsImpl;
import com.chat_app.service.ChatService;
import com.chat_app.valueobjects.ChatId;
import com.chat_app.valueobjects.ParticipantId;
import com.chat_app.valueobjects.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message sendMessage(
            Message message,
            SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("Message received: " + message);

        return message;
    }

    @GetMapping("/chats")
    public ResponseEntity<?> get(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserId userId = userDetails.getId();

        List<ChatDto> chats = this.chatService.getAllChats(new ParticipantId(userId.value()));

        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @GetMapping("/chats/{chatId}")
    public ResponseEntity<?> getDetails(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID chatId
    ) {
        UserId userId = userDetails.getId();
        ChatDetailsDto details = chatService.getChatDetails(ChatId.from(chatId), new ParticipantId(userId.value()));

        return new ResponseEntity<>(details, HttpStatus.OK);
    }
}
