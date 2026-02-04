package com.chat_app.domain.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record SenderId(UUID value) {
    public SenderId {
        Assert.notNull(value, "Sender id cannot be null");
    }

    public static SenderId from(UUID value) {
        return new SenderId(value);
    }
}