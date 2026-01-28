package com.chat_app.application.dto;

import com.chat_app.domain.entity.Chat;
import com.chat_app.presentation.enums.ChatType;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ChatDto(
        @NotNull UUID id,
        @NotNull ChatType type,
        @NotNull String name,
        @NotNull List<UUID> participants
) {
    public static ChatDto from(Chat chat) {
        return new ChatDto(
                chat.getId().value(),
                ChatType.valueOf(chat.getType().name()),
                chat.getName(),
                chat.getParticipants().stream()
                        .map(participant -> participant.getParticipantId().value())
                        .toList()
        );
    }
}
