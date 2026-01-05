package com.chat_app.domain.factory;

import java.util.UUID;
import java.util.function.Function;

public class IdFactory {
    public static <T> T generateId(Function<UUID, T> function) {
        return function.apply(UUID.randomUUID());
    }
}
