package com.chat_app.response;

import com.chat_app.entity.User;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email
) {
    public static UserResponse create(User user) {
        return new UserResponse(
                user.getId().value(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
