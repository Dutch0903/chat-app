package com.chat_app.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record StartGroupChatRequest(
        @NotNull String name,
        @NotNull @Size(min = 2) List<@NotNull UUID> participants
) {
}
