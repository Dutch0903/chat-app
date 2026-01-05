package com.chat_app.presentation.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddParticipantRequest(@NotNull UUID userId) {}
