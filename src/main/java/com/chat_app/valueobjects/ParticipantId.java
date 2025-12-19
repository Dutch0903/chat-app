package com.chat_app.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record ParticipantId(UUID value) {
    public ParticipantId {
        Assert.notNull(value, "Chat participant id cannot be null");
    }
}
