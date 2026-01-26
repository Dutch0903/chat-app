package com.chat_app.infrastructure.websocket.message;

import com.chat_app.infrastructure.websocket.MessageType;

import java.util.UUID;

public record UserOnlineMessage(
        UUID userId,
        Long timestamp,
        MessageType type
) {
    public static UserOnlineMessage create(UUID userId, Long timestamp) {
        return new UserOnlineMessage(
                userId,
                timestamp,
                MessageType.USER_ONLINE
        );
    }
}
