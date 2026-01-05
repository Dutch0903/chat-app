package com.chat_app.testdata;

import com.chat_app.domain.type.ChatParticipantRole;
import com.chat_app.infrastructure.repository.jdbc.data.ChatParticipantData;
import com.chat_app.infrastructure.repository.jdbc.data.id.ChatParticipantId;

import static com.chat_app.testdata.ChatParticipantIdBuilder.aChatParticipantId;

public class ChatParticipantDataBuilder {
    private ChatParticipantId chatParticipantId = aChatParticipantId().build();
    private ChatParticipantRole role = ChatParticipantRole.MEMBER;
    private boolean isNew = false;

    public static ChatParticipantDataBuilder aChatParticipantData() {
        return new ChatParticipantDataBuilder();
    }

    public ChatParticipantDataBuilder withChatParticipantId(ChatParticipantId chatParticipantId) {
        this.chatParticipantId = chatParticipantId;
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
        return new ChatParticipantData(chatParticipantId, role, isNew);
    }
}
