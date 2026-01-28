package com.chat_app.testdata;

import com.chat_app.domain.entity.Participant;
import com.chat_app.domain.enums.ParticipantRole;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;

import static com.chat_app.testdata.ChatIdBuilder.aChatId;
import static com.chat_app.testdata.ParticipantIdBuilder.aParticipantId;

public class ParticipantBuilder {
    private ChatId chatId = aChatId().build();
    private ParticipantId participantId = aParticipantId().build();
    private ParticipantRole role = ParticipantRole.MEMBER;

    public static ParticipantBuilder aParticipant() {
        return new ParticipantBuilder();
    }

    public ParticipantBuilder withChatId(ChatId chatId) {
        this.chatId = chatId;
        return this;
    }

    public ParticipantBuilder withParticipantId(ParticipantId participantId) {
        this.participantId = participantId;
        return this;
    }

    public ParticipantBuilder withRole(ParticipantRole role) {
        this.role = role;
        return this;
    }

    public Participant build() {
        return new Participant(chatId, participantId, role);
    }
}
