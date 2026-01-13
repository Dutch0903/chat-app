package com.chat_app.presentation.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@NotNull
public record StartPrivateChatRequest(
        @NotNull UUID participantId
) {
}
