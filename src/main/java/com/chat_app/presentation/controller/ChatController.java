package com.chat_app.presentation.controller;

import com.chat_app.application.dto.ChatDetailsDto;
import com.chat_app.application.dto.ChatDto;
import com.chat_app.application.mapper.ChatMapper;
import com.chat_app.application.service.ChatService;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;
import com.chat_app.domain.valueobjects.UserId;
import com.chat_app.infrastructure.security.UserDetailsImpl;
import com.chat_app.presentation.request.StartGroupChatRequest;
import com.chat_app.presentation.request.StartPrivateChatRequest;
import com.chat_app.presentation.response.ChatDetailsResponse;
import com.chat_app.presentation.response.ChatResponse;
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

    @Autowired
    private ChatMapper chatMapper;

    @GetMapping("/chats")
    public ResponseEntity<List<ChatResponse>> getChats(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserId userId = userDetails.getId();

        List<ChatDto> chats = this.chatService.getAllChats(new ParticipantId(userId.value()));
        List<ChatResponse> responses = chats.stream()
                .map(chatMapper::toChatResponse)
                .toList();

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/chats/{chatId}")
    public ResponseEntity<ChatDetailsResponse> getChatDetails(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID chatId
    ) {
        UserId userId = userDetails.getId();
        ChatDetailsDto details = chatService.getChatDetails(ChatId.from(chatId), new ParticipantId(userId.value()));

        return new ResponseEntity<>(chatMapper.toChatDetailsResponse(details), HttpStatus.OK);
    }

    @PostMapping("/chats/private")
    public ResponseEntity<ChatDetailsResponse> startPrivateChat(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody StartPrivateChatRequest startPrivateChatRequest
    ) {
        UserId userId = userDetails.getId();

        ChatDetailsDto details = chatService.startPrivateChat(
                ParticipantId.from(userId.value()),
                ParticipantId.from(startPrivateChatRequest.participantId())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(chatMapper.toChatDetailsResponse(details));
    }

    @PostMapping("/chats/group")
    public ResponseEntity<ChatDetailsResponse> startGroupChat(
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

        return ResponseEntity.status(HttpStatus.CREATED).body(chatMapper.toChatDetailsResponse(details));
    }
}


