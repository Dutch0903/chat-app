package com.chat_app.infrastructure.websocket.message;

import com.chat_app.infrastructure.websocket.MessageType;

import java.util.UUID;

public record UserOfflineMessage(
        UUID userId,
        Long timestamp,
        MessageType type
) {
    public static UserOfflineMessage create(
            UUID userId,
            Long timestamp
    ) {
        return new UserOfflineMessage(
                userId,
                timestamp,
                MessageType.USER_OFFLINE
        );
    }
}
