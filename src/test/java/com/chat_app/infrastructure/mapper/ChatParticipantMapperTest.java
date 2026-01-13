package com.chat_app.infrastructure.mapper;

import com.chat_app.domain.entity.ChatParticipant;
import com.chat_app.infrastructure.repository.jdbc.data.ChatParticipantData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.chat_app.testdata.ChatParticipantBuilder.aChatParticipant;
import static com.chat_app.testdata.ChatParticipantDataBuilder.aChatParticipantData;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ChatParticipantMapperTest {
    @InjectMocks
    private ChatParticipantMapper chatParticipantMapper;

    @Test
    public void toData_ReturnsCorrectData() {
        ChatParticipant chatParticipant = aChatParticipant().build();

        ChatParticipantData data = chatParticipantMapper.toData(chatParticipant);

        assertEquals(chatParticipant.getChatId().value(), data.getChatId());
        assertEquals(chatParticipant.getParticipantId().value(), data.getParticipantId());
        assertEquals(chatParticipant.getParticipantRole(), data.getRole());
    }

    @Test
    public void toEntity_ReturnsCorrectData() {
        ChatParticipantData chatParticipantData = aChatParticipantData().build();

        ChatParticipant chatParticipant = chatParticipantMapper.toEntity(chatParticipantData);

        assertEquals(chatParticipantData.getChatId(), chatParticipant.getChatId().value());
        assertEquals(chatParticipantData.getParticipantId(), chatParticipant.getParticipantId().value());
        assertEquals(chatParticipantData.getRole(), chatParticipant.getParticipantRole());
    }
}
