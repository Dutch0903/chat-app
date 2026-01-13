package com.chat_app.testdata;

import com.chat_app.domain.entity.ChatParticipant;
import com.chat_app.domain.type.ParticipantRole;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;

import static com.chat_app.testdata.ChatIdBuilder.aChatId;
import static com.chat_app.testdata.ParticipantIdBuilder.aParticipantId;

public class ChatParticipantBuilder {
    private ChatId chatId = aChatId().build();
    private ParticipantId participantId = aParticipantId().build();
    private ParticipantRole role = ParticipantRole.MEMBER;

    public static ChatParticipantBuilder aChatParticipant() {
        return new ChatParticipantBuilder();
    }

    public  ChatParticipantBuilder withChatId(ChatId chatId) {
        this.chatId = chatId;
        return this;
    }

    public ChatParticipantBuilder withParticipantId(ParticipantId participantId) {
        this.participantId = participantId;
        return this;
    }

    public ChatParticipantBuilder withRole(ParticipantRole role) {
        this.role = role;
        return this;
    }

    public ChatParticipant build() {
        return new ChatParticipant(chatId, participantId, role);
    }
}
