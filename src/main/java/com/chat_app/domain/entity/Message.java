package com.chat_app.domain.entity;

import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.MessageId;
import com.chat_app.domain.valueobjects.ParticipantId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class Message {
    private MessageId id;
    private ChatId chatId;
    private ParticipantId senderId;
    private String content;

    private OffsetDateTime createdAt;
}
