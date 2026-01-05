package com.chat_app.presentation.response;

import com.chat_app.domain.entity.User;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId().value(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
