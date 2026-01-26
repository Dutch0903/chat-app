package com.chat_app.domain.event;

import com.chat_app.domain.valueobjects.UserId;

import java.time.Instant;

public record UserWentOfflineEvent(
        UserId userId,
        Instant timestamp
) {}
