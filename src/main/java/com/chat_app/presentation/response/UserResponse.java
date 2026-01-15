package com.chat_app.presentation.response;

import com.chat_app.domain.entity.User;
import com.chat_app.infrastructure.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "User")
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
