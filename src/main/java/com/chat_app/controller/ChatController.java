package com.chat_app.controller;

import com.chat_app.entity.Chat;
import com.chat_app.entity.Message;
import com.chat_app.response.ChatResponse;
import com.chat_app.security.UserDetailsImpl;
import com.chat_app.service.ChatService;
import com.chat_app.valueobjects.ChatParticipantId;
import com.chat_app.valueobjects.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

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

        List<Chat> chats = this.chatService.getAllChats(new ChatParticipantId(userId.value()));

        return new ResponseEntity<>(chats.stream().map(ChatResponse::from), HttpStatus.OK);
    }
}
