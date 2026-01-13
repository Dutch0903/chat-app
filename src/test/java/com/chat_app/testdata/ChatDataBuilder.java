package com.chat_app.testdata;

import com.chat_app.domain.type.ChatType;
import com.chat_app.infrastructure.repository.jdbc.data.ChatData;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ChatDataBuilder {
    private UUID id =  UUID.randomUUID();
    private String type = ChatType.PRIVATE.name();
    private String name = "Random chat";
    private OffsetDateTime createdAt = OffsetDateTime.now();
    private OffsetDateTime updatedAt = OffsetDateTime.now();
    private boolean isNew = false;

    public static ChatDataBuilder aChatData() {
        return new ChatDataBuilder();
    }

    public ChatDataBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public ChatDataBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public ChatDataBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ChatDataBuilder withCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ChatDataBuilder withUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public ChatDataBuilder withIsNew(boolean isNew) {
        this.isNew = isNew;
        return this;
    }

    public ChatData build() {
        return new ChatData(
                id,
                type,
                name,
                createdAt,
                updatedAt,
                isNew
        );
    }
}
