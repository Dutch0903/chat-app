package com.chat_app.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record ChatMessageId(UUID value) {
    public ChatMessageId{
        Assert.notNull(value, "Chat message id must not be null");
    }
}
