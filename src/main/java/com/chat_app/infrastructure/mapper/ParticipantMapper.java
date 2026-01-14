package com.chat_app.infrastructure.mapper;

import com.chat_app.domain.entity.Participant;
import com.chat_app.domain.valueobjects.ChatId;
import com.chat_app.domain.valueobjects.ParticipantId;
import com.chat_app.infrastructure.repository.jdbc.data.ParticipantData;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMapper {

    public ParticipantData toData(Participant participant) {
        return new ParticipantData(
                null,
                participant.getChatId().value(),
                participant.getParticipantId().value(),
                participant.getParticipantRole()
        );
    }

    public Participant toEntity(ParticipantData participantData) {
        return new Participant(
            new ChatId(participantData.getChatId()),
            new ParticipantId(participantData.getParticipantId()),
            participantData.getRole()
        );
    }
}
