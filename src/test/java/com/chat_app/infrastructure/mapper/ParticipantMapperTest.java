package com.chat_app.infrastructure.mapper;

import com.chat_app.domain.entity.Participant;
import com.chat_app.infrastructure.repository.jdbc.data.ParticipantData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.chat_app.testdata.ParticipantBuilder.aParticipant;
import static com.chat_app.testdata.ChatParticipantDataBuilder.aChatParticipantData;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ParticipantMapperTest {
    @InjectMocks
    private ParticipantMapper participantMapper;

    @Test
    public void toData_ReturnsCorrectData() {
        Participant participant = aParticipant().build();

        ParticipantData data = participantMapper.toData(participant);

        assertEquals(participant.getChatId().value(), data.getChatId());
        assertEquals(participant.getParticipantId().value(), data.getParticipantId());
        assertEquals(participant.getParticipantRole(), data.getRole());
    }

    @Test
    public void toEntity_ReturnsCorrectData() {
        ParticipantData participantData = aChatParticipantData().build();

        Participant participant = participantMapper.toEntity(participantData);

        assertEquals(participantData.getChatId(), participant.getChatId().value());
        assertEquals(participantData.getParticipantId(), participant.getParticipantId().value());
        assertEquals(participantData.getRole(), participant.getParticipantRole());
    }
}
