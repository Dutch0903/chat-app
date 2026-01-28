package com.chat_app.presentation.response;

import com.chat_app.presentation.enums.ChatType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Schema(name = "ChatDetails", description = "Detailed chat information")
public record ChatDetailsResponse(
        @NotNull
        @Schema(description = "Chat ID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @NotNull
        @Schema(description = "Chat type")
        ChatType type,

        @Schema(description = "Chat name", example = "General")
        String name,

        @NotNull
        @Schema(description = "Chat creation timestamp")
        OffsetDateTime createdAt,

        @NotNull
        @Schema(description = "Chat last update timestamp")
        OffsetDateTime updatedAt,

        @Schema(description = "Timestamp of the last message")
        OffsetDateTime lastMessageAt,

        @Schema(description = "ID of the user who sent the last message")
        UUID lastMessageSenderId,

        @NotNull
        @Schema(description = "List of participant IDs")
        List<UUID> participants,

        @Schema(description = "Number of unread messages")
        Integer unreadCount
) {
}
