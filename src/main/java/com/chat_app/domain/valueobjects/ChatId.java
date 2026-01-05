package com.chat_app.domain.valueobjects;

import io.jsonwebtoken.lang.Assert;

import java.util.UUID;

public record ChatId(UUID value) {
    public ChatId{
        Assert.notNull(value, "Chat id cannot be null");
    }

    public static ChatId from(UUID value) {
        return new ChatId(value);
    }
}
