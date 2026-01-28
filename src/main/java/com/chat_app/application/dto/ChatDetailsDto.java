package com.chat_app.application.dto;

import com.chat_app.domain.entity.Chat;
import com.chat_app.presentation.enums.ChatType;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record ChatDetailsDto(
        UUID id,
        ChatType type,
        String name,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        OffsetDateTime lastMessageAt,
        UUID lastMessageSenderId,
        List<UUID> participants,
        Integer unreadCount
) {
    public static ChatDetailsDto from(Chat chat) {
        return new ChatDetailsDto(
                chat.getId().value(),
                ChatType.valueOf(chat.getType().name()),
                chat.getName(),
                chat.getCreatedAt(),
                chat.getUpdatedAt(),
                OffsetDateTime.now(),
                UUID.randomUUID(),
                chat.getParticipants().stream().map(chatParticipant -> chatParticipant.getParticipantId().value()).toList(),
                0
        );
    }
}
