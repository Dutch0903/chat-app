package com.chat_app.presentation.response;

import com.chat_app.presentation.enums.ChatType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Schema(name = "Chat", description = "Chat details response")
public record ChatResponse(
        @NotNull
        @Schema(description = "Chat ID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @NotNull
        @Schema(description = "Chat type")
        ChatType type,

        @Schema(description = "Chat name", example = "General")
        String name,

        @NotNull
        @Schema(description = "List of participant IDs")
        List<UUID> participants
) {
}
