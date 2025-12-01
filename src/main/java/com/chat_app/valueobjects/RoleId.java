package com.chat_app.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record RoleId(UUID value) {
    public RoleId{
        Assert.notNull(value, "Role Id cannot be null");
    }
}
