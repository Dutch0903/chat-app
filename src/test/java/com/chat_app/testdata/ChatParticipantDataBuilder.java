package com.chat_app.testdata;

import com.chat_app.domain.type.ChatParticipantRole;
import com.chat_app.infrastructure.repository.jdbc.data.ChatParticipantData;

import java.util.UUID;

public class ChatParticipantDataBuilder {
    private UUID chatId = UUID.randomUUID();
    private UUID participantId = UUID.randomUUID();
    private ChatParticipantRole role = ChatParticipantRole.MEMBER;
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

    public ChatParticipantDataBuilder withRole(ChatParticipantRole role) {
        this.role = role;
        return this;
    }

    public ChatParticipantDataBuilder asNew(boolean isNew) {
        this.isNew = isNew;
        return this;
    }

    public ChatParticipantData build() {
        return new ChatParticipantData(
                UUID.randomUUID(),
                chatId,
                participantId,
                role,
                isNew
        );
    }
}
