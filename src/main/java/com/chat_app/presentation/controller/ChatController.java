package com.chat_app.presentation.controller;

import com.chat_app.application.dto.ChatDetailsDto;
import com.chat_app.application.dto.ChatDto;
import com.chat_app.domain.entity.Message;
import com.chat_app.presentation.request.AddParticipantRequest;
import com.chat_app.presentation.request.StartDirectChatRequest;
import com.chat_app.presentation.request.StartGroupChatRequest;
import com.chat_app.infrastructure.security.UserDetailsImpl;
import com.chat_app.application.service.ChatService;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;
import com.chat_app.domain.valueobjects.UserId;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @PostMapping("/chats/direct")
    public ResponseEntity<?> startDirectChat(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody StartDirectChatRequest startDirectChatRequest
    ) {
        UserId userId = userDetails.getId();

        ChatDetailsDto details = chatService.startDirectChat(
                ParticipantId.from(userId.value()),
                ParticipantId.from(startDirectChatRequest.participantId())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(details);
    }

    @PostMapping("/chats/group")
    public ResponseEntity<?> startGroupChat(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody StartGroupChatRequest startGroupChatRequest
    ) {
        UserId userId = userDetails.getId();

        List<ParticipantId> participantIds = new  ArrayList<>();
        participantIds.add(ParticipantId.from(userId.value()));
        startGroupChatRequest.participants().forEach(id -> {
            participantIds.add(ParticipantId.from(id));
        });

        ChatDetailsDto details = chatService.startGroupChat(
                startGroupChatRequest.name(),
                participantIds
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(details);
    }
}
