package com.chat_app.testdata;

import com.chat_app.domain.type.ParticipantRole;
import com.chat_app.infrastructure.repository.jdbc.data.ParticipantData;

import java.util.UUID;

public class ChatParticipantDataBuilder {
    private UUID chatId = UUID.randomUUID();
    private UUID participantId = UUID.randomUUID();
    private ParticipantRole role = ParticipantRole.MEMBER;
    private boolean isNew = false;

    public static ChatParticipantDataBuilder aChatParticipantData() {
        return new ChatParticipantDataBuilder();
    }

    public ChatParticipantDataBuilder withChatId(UUID chatId) {
        this.chatId = chatId;
        return this;
    }

    public ChatParticipantDataBuilder withParticipantId(UUID participantId) {
        this.participantId = participantId;
        return this;
    }

    public ChatParticipantDataBuilder withRole(ParticipantRole role) {
        this.role = role;
        return this;
    }

    public ChatParticipantDataBuilder asNew(boolean isNew) {
        this.isNew = isNew;
        return this;
    }

    public ParticipantData build() {
        return new ParticipantData(
                UUID.randomUUID(),
                chatId,
                participantId,
                role,
                isNew
        );
    }
}
