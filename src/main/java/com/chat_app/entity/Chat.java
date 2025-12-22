package com.chat_app.entity;

import com.chat_app.type.ChatType;
import com.chat_app.valueobjects.ChatId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class Chat {
    private ChatId id;
    private ChatType type;
    private String name;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Chat(ChatId id, ChatType type) {
        this.id = id;
        this.type = type;

        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    public Chat(ChatId id, ChatType type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
}
