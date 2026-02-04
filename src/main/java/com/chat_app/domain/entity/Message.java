package com.chat_app.domain.entity;

import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.MessageId;
import com.chat_app.domain.valueobjects.SenderId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class Message {
    private final MessageId id;
    private final ChatId chatId;
    private final SenderId senderId;
    private final String content;
    private final OffsetDateTime createdAt;
}
