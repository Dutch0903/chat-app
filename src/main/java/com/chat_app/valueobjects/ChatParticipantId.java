package com.chat_app.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record ChatParticipantId(UUID value) {
    public ChatParticipantId{
        Assert.notNull(value, "Chat participant id cannot be null");
    }
}
