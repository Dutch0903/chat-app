package com.chat_app.presentation.controller;

import com.chat_app.application.dto.ChatDetailsDto;
import com.chat_app.application.dto.ChatDto;
import com.chat_app.application.service.ChatService;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;
import com.chat_app.domain.valueobjects.UserId;
import com.chat_app.infrastructure.security.UserDetailsImpl;
import com.chat_app.presentation.request.StartGroupChatRequest;
import com.chat_app.presentation.request.StartPrivateChatRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/chats")
    public ResponseEntity<?> getChats(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserId userId = userDetails.getId();

        List<ChatDto> chats = this.chatService.getAllChats(new ParticipantId(userId.value()));

        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @GetMapping("/chats/{chatId}")
    public ResponseEntity<?> getChatDetails(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID chatId
    ) {
        UserId userId = userDetails.getId();
        ChatDetailsDto details = chatService.getChatDetails(ChatId.from(chatId), new ParticipantId(userId.value()));

        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @PostMapping("/chats/private")
    public ResponseEntity<?> startPrivateChat(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody StartPrivateChatRequest startPrivateChatRequest
    ) {
        UserId userId = userDetails.getId();

        ChatDetailsDto details = chatService.startPrivateChat(
                ParticipantId.from(userId.value()),
                ParticipantId.from(startPrivateChatRequest.participantId())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(details);
    }

    @PostMapping("/chats/group")
    public ResponseEntity<?> startGroupChat(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody StartGroupChatRequest startGroupChatRequest
    ) {
        UserId userId = userDetails.getId();

        List<ParticipantId> participantIds = new ArrayList<>();
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
