package com.chat_app.testdata;

import com.chat_app.infrastructure.repository.jdbc.data.id.ChatParticipantId;

import java.util.UUID;

public class ChatParticipantIdBuilder {
    private UUID chatId = UUID.randomUUID();
    private UUID participantId = UUID.randomUUID();

    public static ChatParticipantIdBuilder aChatParticipantId() {
        return new ChatParticipantIdBuilder();
    }

    public ChatParticipantIdBuilder withParticipantId(UUID participantId) {
        this.participantId = participantId;
        return this;
    }

    public ChatParticipantIdBuilder withChatId(UUID chatId) {
        this.chatId = chatId;
        return this;
    }

    public ChatParticipantId build() {
        return new ChatParticipantId(chatId, participantId);
    }
}
