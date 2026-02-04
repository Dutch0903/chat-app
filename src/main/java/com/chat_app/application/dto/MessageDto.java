package com.chat_app.application.dto;

import com.chat_app.domain.entity.Message;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MessageDto(
        UUID id,
        UUID chatId,
        UUID senderId,
        String content,
        OffsetDateTime timestamp
) {
    public static MessageDto from(Message message) {
        return new MessageDto(
                message.getId().value(),
                message.getChatId().value(),
                message.getSenderId().value(),
                message.getContent(),
                message.getCreatedAt()
        );
    }
}
