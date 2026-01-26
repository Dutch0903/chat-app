package com.chat_app.domain.event;

import com.chat_app.domain.valueobjects.UserId;

import java.time.Instant;

public record UserWentOnlineEvent(
        UserId userId,
        Instant timestamp
) {}
