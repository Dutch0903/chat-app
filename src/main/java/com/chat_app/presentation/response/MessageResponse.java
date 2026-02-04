package com.chat_app.presentation.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MessageResponse(
        UUID id,
        UUID chatId,
        UUID senderId,
        String content,
        OffsetDateTime timestamp
) {

}
