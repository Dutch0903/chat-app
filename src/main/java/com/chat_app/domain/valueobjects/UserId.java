package com.chat_app.domain.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record UserId(UUID value) implements Comparable<UserId> {
    public UserId{
        Assert.notNull(value, "User Id cannot be null");
    }

    public static UserId create() {
        return new UserId(UUID.randomUUID());
    }

    @Override
    public int compareTo(UserId o) {
        return this.value.compareTo(o.value);
    }
}
