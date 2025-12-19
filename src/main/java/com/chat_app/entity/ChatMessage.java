package com.chat_app.entity;

import com.chat_app.valueobjects.ChatId;
import com.chat_app.valueobjects.ChatMessageId;
import com.chat_app.valueobjects.ParticipantId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class ChatMessage {
    private ChatMessageId id;
    private ChatId chatId;
    private ParticipantId senderId;
    private String content;

    private OffsetDateTime createdAt;
}
