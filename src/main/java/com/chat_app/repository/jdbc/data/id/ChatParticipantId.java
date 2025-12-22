package com.chat_app.repository.jdbc.data.id;

import java.util.UUID;

public record ChatParticipantId(UUID chatId, UUID participantId) {}
