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

}
