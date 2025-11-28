package com.chat_app.response;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record JwtResponse(
        String token,
        String type,
        UUID id,
        String username,
        String email,
        List<String> roles
) {
}
