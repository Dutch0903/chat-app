package com.chat_app.dto;

import com.chat_app.entity.Chat;

import java.util.UUID;

public record ChatDto(
        UUID id,
        String type,
        String name
) {
    public static ChatDto from(Chat chat) {
        return new ChatDto(
                chat.getId().value(),
                chat.getType().name(),
                chat.getName()
        );
    }
}
