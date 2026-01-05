package com.chat_app.domain.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record ParticipantId(UUID value) {
    public ParticipantId {
        Assert.notNull(value, "Chat participant id cannot be null");
    }

    public static ParticipantId from(UUID value) {
        return new ParticipantId(value);
    }
}
