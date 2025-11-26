package com.chat_app.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record UserId(UUID value) {
    public UserId{
        Assert.notNull(value, "User Id cannot be null");
    }

    public static UserId create() {
        return new UserId(UUID.randomUUID());
    }
}
