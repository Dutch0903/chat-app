package com.chat_app.dto;

import com.chat_app.entity.Chat;
import com.chat_app.entity.ChatParticipant;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record ChatDetailsDto(
        UUID id,
        String type,
        String name,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime lastMessageAt,
        UUID lastMessageSenderId,
        List<UUID> participants,
        Integer unreadCount
) {
    public static ChatDetailsDto from(Chat chat, List<ChatParticipant> chatParticipants) {
        return new ChatDetailsDto(
                chat.getId().value(),
                chat.getType().name(),
                chat.getName(),
                chat.getCreatedAt(),
                chat.getUpdatedAt(),
                OffsetDateTime.now(),
                UUID.randomUUID(),
                chatParticipants.stream().map(chatParticipant -> chatParticipant.getParticipantId().value()).toList(),
                0
        );
    }
}
