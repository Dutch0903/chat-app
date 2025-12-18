package com.chat_app.valueobjects;

import io.jsonwebtoken.lang.Assert;

import java.util.UUID;

public record ChatId(UUID value) {
    public ChatId{
        Assert.notNull(value, "Chat id cannot be null");
    }
}
