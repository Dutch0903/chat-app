package com.chat_app.domain.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record MessageId(UUID value) {
    public MessageId {
        Assert.notNull(value, "Chat message id must not be null");
    }

    public static MessageId from(UUID value) {
        return new MessageId(value);
    }
}
