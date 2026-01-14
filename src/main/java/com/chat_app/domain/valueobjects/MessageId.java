package com.chat_app.domain.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record MessageId(UUID value) {
    public MessageId {
        Assert.notNull(value, "Chat message id must not be null");
    }
}
