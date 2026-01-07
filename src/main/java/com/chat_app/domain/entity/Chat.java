package com.chat_app.domain.entity;

import com.chat_app.domain.type.ChatType;
import com.chat_app.domain.valueobjects.ChatId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class Chat {
    private ChatId id;
    private ChatType type;
    private String name;

    private List<ChatParticipant> participants;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Chat(ChatId id, ChatType type, List<ChatParticipant> participants, String name) {
        this.id = id;
        this.type = type;
        this.participants = participants;
        this.name = name;

        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
}
