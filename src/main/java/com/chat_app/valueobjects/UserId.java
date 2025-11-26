package com.chat_app.valueobjects;

import java.util.UUID;

public record UserId(UUID value) {
    public UserId{
        if (value == null) {
            throw new IllegalArgumentException("User Id cannot be null");
        }
    }

    public static UserId create() {
        return new UserId(UUID.randomUUID());
    }
}
