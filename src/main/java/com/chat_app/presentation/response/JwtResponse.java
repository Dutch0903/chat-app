package com.chat_app.presentation.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record JwtResponse(
        @NotNull String token,
        @NotNull String type,
        @NotNull UUID id,
        @NotNull String username,
        @NotNull String email,
        @NotNull List<String> roles
) {
}
