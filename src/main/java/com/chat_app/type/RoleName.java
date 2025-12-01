package com.chat_app.type;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum RoleName {
    USER,
    ADMIN;

    private static final Set<String> names = Arrays.stream(values())
            .map(RoleName::name)
            .collect(Collectors.toSet());

    public static boolean isValid(String val) {
        return names.contains(val);
    }
}
