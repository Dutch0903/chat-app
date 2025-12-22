package com.chat_app.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddParticipantRequest(@NotNull UUID userId) {}
