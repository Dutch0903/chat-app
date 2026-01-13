package com.chat_app.testdata;

import com.chat_app.domain.valueobjects.ChatId;

import java.util.UUID;

public class ChatIdBuilder {
    private UUID value = UUID.randomUUID();

    public static ChatIdBuilder aChatId() {
        return new ChatIdBuilder();
    }

    public ChatIdBuilder withValue(UUID value) {
        this.value = value;
        return this;
    }

    public ChatId build() {
        return ChatId.from(value);
    }
}
