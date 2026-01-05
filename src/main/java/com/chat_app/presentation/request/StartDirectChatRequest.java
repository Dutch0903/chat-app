package com.chat_app.presentation.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@NotNull
public record StartDirectChatRequest(
        @NotNull UUID participantId
) {
}
