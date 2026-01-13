package com.chat_app.testdata;

import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;

import java.util.UUID;

public class ParticipantIdBuilder {
    private UUID value = UUID.randomUUID();

    public static ParticipantIdBuilder aParticipantId() {
        return new ParticipantIdBuilder();
    }

    public ParticipantIdBuilder withValue(UUID value) {
        this.value = value;
        return this;
    }

    public ParticipantId build() {
        return ParticipantId.from(value);
    }
}
