package com.chat_app.presentation.response;


import java.time.Instant;
import java.util.UUID;

public record MessageResponse(
        UUID senderId,
        String content,
        Instant timestamp
) {

}
