package com.chat_app.response;

import com.chat_app.entity.Chat;

import java.util.UUID;

public record ChatResponse(
        UUID id,
        String type,
        String name
) {
    public static ChatResponse from(Chat chat) {
        return new ChatResponse(
                chat.getId().value(),
                chat.getType().name(),
                chat.getName()
        );
    }
}
